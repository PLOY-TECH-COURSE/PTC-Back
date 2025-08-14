package org.plteco.ploytechcourse.application.grading.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.grading.form.dto.req.CreateFormDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.CreateQuestion;
import org.plteco.ploytechcourse.api.grading.form.dto.req.RequestScoreDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormDetailResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.GradingFormResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.PresentationOrderResponseDto;
import org.plteco.ploytechcourse.api.grading.form.dto.res.StudentScoreDto;
import org.plteco.ploytechcourse.application.grading.command.PresentationOrderCommand;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.repository.AnnouncementRepository;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.grading.model.*;
import org.plteco.ploytechcourse.domain.grading.repository.GradingAnswerRepository;
import org.plteco.ploytechcourse.domain.grading.repository.GradingFormRepository;
import org.plteco.ploytechcourse.domain.grading.repository.GradingPresentationOrderRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GradingServiceApplicationImpl implements GradingServiceApplication {
    private final StudentRepository studentRepository; // 학생 레포지토리
    private final GradingFormRepository gradingFormRepository; // 채점 폼 레포지토리
    private final GradingAnswerRepository gradingAnswerRepository;
    private final GradingPresentationOrderRepository gradingPresentationOrderRepository;
    private final UserContextUtil userContextUtil;
    private final AnnouncementRepository announcementRepository; // 공지사항 서비스 추가
    private final UserRepository  userRepository;

    // 채점 폼 만드는 함수
    @Override
    @Transactional
    public void createGradingForm(CreateFormDto createFormDto) {
        int studentCounts = studentRepository.countByLatestGeneration(); // 피채점자 수
        int graderCounts = createFormDto.getGrader_counts(); // 채점자 수
        int questionCounts = createFormDto.getQuestions().size(); // 질문 수

        // ✅ 수정: 올바른 expectedTotalAnswers 계산
        // 각 학생마다 각 평가자가 모든 질문에 답해야 하므로
        GradingForm form = GradingForm.builder()
                .title(createFormDto.getTitle())
                .description(createFormDto.getDescription())
                .graderCount(graderCounts)
                .expectedTotalAnswers(studentCounts * graderCounts * questionCounts) // ✅ 질문 수 추가
                .build();

        for (CreateQuestion q : createFormDto.getQuestions()) {
            form.addQuestionWithScores(q.getQuestion(), q.getScores()); // 질문, 점수 추가
        }

        gradingFormRepository.save(form); // 한 번만 저장하면 Cascade로 모두 저장됨
    }

    @Override
    public List<GradingFormResponseDto> getAllGradingForm() {
        List<GradingForm> gradingFormList = gradingFormRepository.findAll();

        return gradingFormList.stream()
                .map(GradingFormResponseDto::fromGradingForm)
                .toList();
    }

    @Transactional
    @Override
    public void applyPresentationOrders(Long formId, PresentationOrderCommand cmd) {
        GradingForm form = gradingFormRepository.findById(formId)
                .orElseThrow(() -> new PltecoException("채점폼 없음", HttpStatus.NOT_FOUND));

        // 이미 채점된 학생 차단
        Set<Long> gradedIds = gradingAnswerRepository.findGradedStudentIdsByFormId(formId);
        List<Long> blocked = cmd.changes().keySet().stream()
                .filter(gradedIds::contains).toList();
        if (!blocked.isEmpty()) {
            throw new PltecoException("이미 채점된 학생 포함: " + blocked, HttpStatus.BAD_REQUEST);
        }

        // 변경된 학생들만 업서트 (getReference로 추가 SELECT 회피)
        cmd.changes().forEach((studentId, newOrder) -> {
            Student ref = studentRepository.getReferenceById(studentId);
            form.setOrCreateOrderFor(ref, newOrder);
        });

        // 폼 전체 중복 검증
        form.assertNoDuplicateOrderIndex();

        // 루트 저장 → 하위까지 영속
        gradingFormRepository.save(form);
    }

    @Override
    public List<PresentationOrderResponseDto> getPresentationOrder(Long formId) {
        List<GradingPresentationOrder> orders = gradingPresentationOrderRepository.findByGradingFormId(formId);

       return orders.stream()
                .map(order -> PresentationOrderResponseDto
                        .fromOrder(
                                order.getStudent().getId(),
                                order.getOrderIndex(),
                                order.getStudent().getUser().getName(),
                                gradingAnswerRepository.existsByFormIdAndStudentId(order.getGradingForm().getId() ,order.getStudent().getId())
                        )
                ).toList();
    }

    @Override
    public GradingFormDetailResponseDto getGradingFormByFormId(Long formId) {
        GradingForm gradingForm = gradingFormRepository.findById(formId).orElseThrow(() -> new PltecoException("해당 폼을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (gradingForm.getPresentationOrders().isEmpty())
            throw new PltecoException("순서를 먼저 입력해야 합니다.", HttpStatus.BAD_REQUEST);

        List<GradingQuestion> gradingQuestions = gradingForm.getGradingQuestions();

        List<GradingFormDetailResponseDto.ResponseQuestionDto> responseQuestionDtos = gradingQuestions.stream()
                .map(question -> GradingFormDetailResponseDto.ResponseQuestionDto.from(
                        question.getId(),
                        question.getQuestion(),
                        question.getGradingScores().stream().map(GradingScore::getScoreValue).toList()
                ))
                .toList();


        User grader = userContextUtil.getCurrentUser();

        int count = 1 + (int) gradingForm.getAnswers().stream()
                .filter(answer -> answer.getGrader().getId().equals(grader.getId()))
                .count();

        GradingPresentationOrder gradingPresentationOrder = gradingForm.getPresentationOrders().stream()
                .filter(order -> order.getOrderIndex() == count).findFirst().get();

        String titleName = gradingPresentationOrder.getStudent().getUser().getName();
        return GradingFormDetailResponseDto.from(
                        gradingForm.getId(),
                        gradingPresentationOrder.getStudent().getId(),
                        gradingForm.getTitle() + "(" + titleName + ")",
                        gradingForm.getGraderCount(),
                        gradingForm.getDescription(),
                        responseQuestionDtos
                );
    }

    @Override
    @Transactional
    public void addScore(Long formId, RequestScoreDto scoreDto) {
        // 1. 현재 평가자(로그인한 사용자) 정보 가져오기
        User grader = userContextUtil.getCurrentUser();
        
        // 2. 평가 폼 조회
        GradingForm gradingForm = gradingFormRepository.findById(formId)
                .orElseThrow(() -> new PltecoException("해당 평가 폼을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 3. 학생 전원 평가 했는지 확인
        int answersCount =  (int) gradingForm.getAnswers().stream()
                .filter(answer -> answer.getGrader().getId().equals(grader.getId()))
                .count();

        if (answersCount == gradingForm.findGradedStudentsCount(grader.getId())) {
            throw new PltecoException("이미 모든 학생을 채점하였습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 평가 대상 학생 조회
        Student student = studentRepository.findById(scoreDto.getStudentId())
                .orElseThrow(() -> new PltecoException("해당 학생을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 5. 중복 평가 방지 - 이미 해당 평가자가 이 학생을 평가했는지 확인
        boolean alreadyGraded = gradingAnswerRepository.existsByFormIdAndGraderIdAndStudentId(
                formId, grader.getId(), student.getId());
        if (alreadyGraded) {
            throw new PltecoException("이미 해당 학생에 대한 평가를 완료했습니다.", HttpStatus.BAD_REQUEST);
        }

        // 6. 각 답변에 대해 GradingAnswer 엔티티 생성 및 저장
        for (RequestScoreDto.Answer answer : scoreDto.getAnswers()) {
            // 질문 조회
            GradingQuestion question = gradingForm.getGradingQuestions().stream()
                    .filter(q -> q.getId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new PltecoException("해당 질문을 찾을 수 없습니다: " + answer.getQuestionId(), HttpStatus.NOT_FOUND));
            
            // 점수 유효성 검증 - 해당 질문에 대해 선택 가능한 점수인지 확인
            boolean validScore = question.getGradingScores().stream()
                    .anyMatch(score -> score.getScoreValue().equals(answer.getScore().intValue()));
            if (!validScore) {
                throw new PltecoException("유효하지 않은 점수입니다. 질문 ID: " + answer.getQuestionId() + ", 점수: " + answer.getScore(), HttpStatus.BAD_REQUEST);
            }
            
            // GradingAnswer 엔티티 생성
            GradingAnswer gradingAnswer = GradingAnswer.builder()
                    .form(gradingForm)
                    .gradingQuestion(question)
                    .student(student)
                    .grader(grader)
                    .score(answer.getScore().intValue())
                    .build();
            
            // 저장
            gradingAnswerRepository.save(gradingAnswer);
            gradingAnswerRepository.flush();
        }

        // 7. 평가 완료 상태 업데이트 확인 및 공지사항 생성
        // ✅ 수정: 올바른 완료 조건 확인
        long totalExpectedAnswers = gradingForm.getExpectedTotalAnswers();
        long currentAnswerCount = gradingAnswerRepository.countByFormId(formId);

        if (currentAnswerCount == totalExpectedAnswers && !gradingForm.isCompleted()) {
            // 평가 완료 상태로 변경
            gradingForm.markAsCompleted();
            gradingFormRepository.save(gradingForm);
            
            // 평가 완료 공지사항 생성
            createGradingCompletionAnnouncement(gradingForm);
        }
    }

    public List<StudentScoreDto> calculateStudentScores(Long formId) {
        // 1. 평가 폼 존재 확인
        GradingForm gradingForm = gradingFormRepository.findById(formId)
                .orElseThrow(() -> new PltecoException("해당 평가 폼을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        
        // 2. 학생별 점수 통계 조회
        List<GradingAnswerRepository.StudentScoreProjection> scoreProjections = 
                gradingAnswerRepository.findStudentScoresByFormId(formId);
        
        // 3. DTO 변환 및 순위 계산
        List<StudentScoreDto> studentScores = scoreProjections.stream()
                .map(projection -> StudentScoreDto.of(
                        projection.getStudentId(),
                        projection.getStudentName(),
                        projection.getAverageScore(),
                        projection.getTotalScore().intValue(),
                        projection.getAnswerCount().intValue()
                ))
                .toList();
        
        // 4. 순위 계산 (평균 점수 기준, 동점자는 총점으로 구분)
        calculateRanks(studentScores);
        
        return studentScores;
    }

    /**
     * 학생들의 순위를 계산하는 메서드
     */
    private List<StudentScoreDto> calculateRanks(List<StudentScoreDto> students) {
        // 불변 리스트를 가변 리스트로 변환
        List<StudentScoreDto> mutableStudents = new ArrayList<>(students);

        // 이제 정렬 가능
        mutableStudents.sort((s1, s2) -> {
            // 평균 점수 기준 내림차순
            int avgCompare = Double.compare(s2.getAverageScore(), s1.getAverageScore());
            if (avgCompare != 0) return avgCompare;

            // 평균이 같다면 총합 기준 내림차순
            return Double.compare(s2.getTotalScore(), s1.getTotalScore());
        });

        // 랭킹 설정
        for (int i = 0; i < mutableStudents.size(); i++) {
            mutableStudents.get(i).setRank(i + 1);
        }

        return mutableStudents;
    }

    ///
    /**
     * 평가 완료 시 자동으로 공지사항을 생성하는 메서드
     */
    private void createGradingCompletionAnnouncement(GradingForm gradingForm) {
        try {

            // 상위 3명 학생 정보 조회
            List<StudentScoreDto> topStudents = calculateStudentScores(gradingForm.getId())
                    .stream()
                    .limit(3)
                    .toList();


            String title = String.format("🏆 %s 결과 발표!", gradingForm.getTitle());

            StringBuilder contentBuilder = new StringBuilder();

            // 상위 3명 정보 추가
            if (!topStudents.isEmpty()) {
                contentBuilder.append("<결과 src=\"");

                List<Integer> aOrder = List.of(1, 0, 2);

                // 1등부터 순서대로 처리
                for (int i : aOrder) {
                    StudentScoreDto student = topStudents.get(i);

                    String studentProfile = studentRepository.findById(student.getStudentId())
                            .orElseThrow(() -> new PltecoException("해당 학생을 찾을 수 없습니다.", HttpStatus.NOT_FOUND))
                            .getUser().getProfile();

                    contentBuilder.append(String.format("%d등:%s %s",
                            student.getRank(), student.getStudentName(), studentProfile));

                    if (i < topStudents.size() - 1) {
                        contentBuilder.append(" ");
                    }
                }

                contentBuilder.append("\"></결과>\n결과를 떠나, 여러분 각자의 열정과 노력이 큰 의미를 만들어주었습니다. 모두 수고 많으셨습니다.");
            }

            String introduction = String.format("%s 평가가 완료되어 자동으로 생성된 공지사항입니다.", gradingForm.getTitle());


            // SUPERADMIN 사용자 찾기
            User superAdmin = userRepository.findByRole(RoleEnum.ROLE_SUPERADMIN);

            if (superAdmin == null) {
                throw new PltecoException("SUPERADMIN 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }

            Announcement announcement = Announcement.builder()
                    .title(title)
                    .user(superAdmin)
                    .content(contentBuilder.toString())
                    .introduction(introduction)
                    .thumbnail("https://storage.googleapis.com/ploytechcourse-version3/391b0b82-c522-4fd5-9a75-5a1488c21b7e")
                    .build();

            announcementRepository.save(announcement);

        } catch (Exception e) {
            System.err.println("평가 완료 공지사항 생성 실패: " + e.getMessage());
        }
    }
}
