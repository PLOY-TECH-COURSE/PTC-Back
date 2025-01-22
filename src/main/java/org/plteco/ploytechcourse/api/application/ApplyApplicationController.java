package org.plteco.ploytechcourse.api.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.application.application.service.ApplyApplicationApplication;
import org.plteco.ploytechcourse.application.application.service.ShowApplicationApplication;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplyApplicationController {
    private final ApplyApplicationApplication applicationApplication;
    private final ShowApplicationApplication showApplicationApplication;

    @PostMapping
    public ResponseEntity<String> apply(@Valid @RequestBody ApplyApplicationDto applyApplicationDto) {
        applicationApplication.applyApplication(applyApplicationDto);
        return ResponseEntity.ok("테크코스 신청이 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ShowApplicationDto>> getApplications() {
        return ResponseEntity.ok(showApplicationApplication.showApplication());
    }
}
