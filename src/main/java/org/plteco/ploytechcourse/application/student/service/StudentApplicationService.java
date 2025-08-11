package org.plteco.ploytechcourse.application.student.service;

import org.plteco.ploytechcourse.api.grading.student.dto.res.StudentResponse;

import java.util.List;

public interface StudentApplicationService {
    List<StudentResponse> getStudentsLatestGeneration();
}
