package org.plteco.ploytechcourse.application.student.service;


import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.student.dto.res.StudentResponse;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentApplicationServiceImpl implements StudentApplicationService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> getStudentsLatestGeneration() {
        List<Student> students = studentRepository.findAllByLatestGeneration();

        return students.stream()
                .map(StudentResponse::from)
                .toList();
    }
}
