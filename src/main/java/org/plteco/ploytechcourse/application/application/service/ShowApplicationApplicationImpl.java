package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.application.service.ShowApplicationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShowApplicationApplicationImpl implements ShowApplicationApplication {

    private final ShowApplicationService showApplicationService;

    @Override
    public List<ShowApplicationDto> showApplication() {
        log.info("신청자 조회 시작합니다.");

        List<TechCourseForm> techCourses = showApplicationService.getTechCourses();

        if (techCourses.isEmpty()) {
            log.warn("신청자 목록이 비어 있습니다. 데이터베이스에 신청자가 없습니다.");
            throw new PltecoException("신청자가 없어서 데이터를 조회할 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        List<ShowApplicationDto> response = techCourses.stream().map(course ->
                ShowApplicationDto.builder()
                        .id(course.getId())
                        .user_id(course.getUser().getId())
                        .name(course.getUser().getName())
                        .email(course.getUser().getEmail())
                        .profile(course.getUser().getProfile())
                        .introduction(course.getIntroduction())
                        .expectation(course.getExpectation())
                        .skill(course.getSkill())
                        .study(course.getStudy())
                        .build()
        ).collect(Collectors.toList());

        log.info("신청자 조회 끝났습니다. 총 {}명의 신청자가 조회되었습니다.", response.size());
        return response;
    }
}
