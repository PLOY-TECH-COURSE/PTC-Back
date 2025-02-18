package org.plteco.ploytechcourse.application.image.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.image.dto.GCSRequest;
import org.plteco.ploytechcourse.application.image.dto.GCSResponse;
import org.plteco.ploytechcourse.domain.image.service.GCSService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class GCSServiceApplicationImpl implements GCSServiceApplication {
    private final GCSService gcsService;
    public GCSResponse getGcsService(GCSRequest request) throws IOException {
        GCSResponse gcsResponse=new GCSResponse();
        gcsResponse.setUrl(gcsService.uploadObject(request.getFile()));
        return gcsResponse;
    }
}
