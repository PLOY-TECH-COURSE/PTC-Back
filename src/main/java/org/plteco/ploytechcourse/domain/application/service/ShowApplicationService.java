package org.plteco.ploytechcourse.domain.application.service;

import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;

import java.util.List;

public interface ShowApplicationService {
    List<TechCourseForm> getTechCourses();
}
