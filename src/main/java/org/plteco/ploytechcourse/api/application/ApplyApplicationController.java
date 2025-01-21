package org.plteco.ploytechcourse.api.application;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.application.application.service.ApplyApplicationApplication;
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
    public String apply(@RequestBody ApplyApplicationDto applyApplicationDto) {
        return applicationApplication.applyApplication(applyApplicationDto);
    }
}
