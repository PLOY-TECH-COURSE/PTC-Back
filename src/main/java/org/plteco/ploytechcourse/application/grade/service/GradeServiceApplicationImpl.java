package org.plteco.ploytechcourse.application.grade.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.grade.dto.CreateFormDto;
import org.plteco.ploytechcourse.application.grade.dto.CreateQuestion;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.grading.model.GradingForm;
import org.plteco.ploytechcourse.domain.grading.model.GradingScore;
import org.plteco.ploytechcourse.domain.grading.repository.GradingFormRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceApplicationImpl implements GradeServiceApplication {
    private final StudentRepository studentRepository; // 학생 레포지토리
    private final GradingFormRepository gradingFormRepository; // 채점 폼 레포지토리


    // 채점 폼 만드는 함수
    @Override
    @Transactional
    public void createGradeForm(CreateFormDto createFormDto) {
        int studentCounts = studentRepository.countByLatestGeneration(); // 피채점자 수
        int graderCounts = createFormDto.getGrader_counts(); // 채점자 수

        GradingForm form = GradingForm.builder()
                .title(createFormDto.getTitle())
                .graderCount(graderCounts)
                .expectedTotalAnswers(studentCounts * graderCounts)
                .build();

        for (CreateQuestion q : createFormDto.getQuestions()) {
            form.addQuestionWithScores(q.getQuestion(), q.getScores()); // 질문, 점수 추가
        }

        gradingFormRepository.save(form); // 한 번만 저장하면 Cascade로 모두 저장됨
        gradingFormRepository.flush();
    }
}
