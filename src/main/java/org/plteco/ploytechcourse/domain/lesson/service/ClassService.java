package org.plteco.ploytechcourse.domain.lesson.service;

import org.plteco.ploytechcourse.application.lesson.dto.ClassDto;
import org.plteco.ploytechcourse.application.lesson.dto.TechCourseDto;
import org.plteco.ploytechcourse.application.lesson.dto.TrackDto;

import java.util.List;

public interface ClassService {
    void SavetechCourse(TechCourseDto techCourseDto);
    List<TechCourseDto> ShowTechCourse();

    void Savetrack(TrackDto trackDto);
    List<TrackDto> ShowTrack();
    List<TrackDto> getTrack(Long generation);

    void Saveclass(ClassDto classDto);
    List<ClassDto> ShowClass();
    List<ClassDto> getClass(Long id);
}
