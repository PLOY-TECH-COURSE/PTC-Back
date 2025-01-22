package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
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
public class ShowApplicationApplicationImpl implements ShowApplicationApplication {

    private final ShowApplicationService showApplicationService;

    @Override
    public List<ShowApplicationDto> showApplication() {
        List<TechCourseForm> techCourses = showApplicationService.getTechCourses();
        if(techCourses.isEmpty()) {
            throw new PltecoException("데이터 베이스 에러", HttpStatus.NOT_FOUND);
        }

        List<ShowApplicationDto> response = techCourses.stream().map(course ->
                ShowApplicationDto.builder()
                        .id(course.getId())
                        .user_id(course.getUser().getId())
                        .name(course.getUser().getName())
                        .email(course.getUser().getEmail())
                        .profile(course.getUser().getProfile())
                        .introduction(course.getIntroduction())
                        .resolution(course.getResolution())
                        .build()
        ).collect(Collectors.toList());

        return response;
    }
}
