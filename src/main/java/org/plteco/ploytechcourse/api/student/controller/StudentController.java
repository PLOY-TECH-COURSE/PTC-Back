package org.plteco.ploytechcourse.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.api.student.dto.res.StudentResponse;
import org.plteco.ploytechcourse.application.student.service.StudentApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentApplicationService studentApplicationService;

    @GetMapping("/latest-generation")
    public ResponseEntity<List<StudentResponse>> getStudentsLatestGeneration() {
        List<StudentResponse> students = studentApplicationService.getStudentsLatestGeneration();
        return new ResponseEntity<>(students ,HttpStatus.OK);
    }

}
