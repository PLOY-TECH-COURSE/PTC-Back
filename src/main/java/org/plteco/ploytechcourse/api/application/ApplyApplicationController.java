package org.plteco.ploytechcourse.api.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.application.application.service.ApplyApplicationApplication;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplyApplicationController {
    private final ApplyApplicationApplication applicationApplication;

    @PostMapping
    public ResponseEntity<String> apply(@Valid @RequestBody ApplyApplicationDto applyApplicationDto) {
        applicationApplication.applyApplication(applyApplicationDto);
        return ResponseEntity.ok("테크코스 신청이 완료되었습니다.");
    }
}
