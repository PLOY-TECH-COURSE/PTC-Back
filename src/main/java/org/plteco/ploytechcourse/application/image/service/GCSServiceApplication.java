package org.plteco.ploytechcourse.application.image.service;

import org.plteco.ploytechcourse.application.image.dto.GCSRequest;
import org.plteco.ploytechcourse.application.image.dto.GCSResponse;

import java.io.IOException;

public interface GCSServiceApplication {

    /**
     * GCS에 파일을 업로드하고 그 URL을 반환합니다.
     *
     * @param request GCSRequest 객체로, 파일이 포함됩니다.
     * @return GCSResponse 객체로 업로드된 파일의 URL을 포함합니다.
     * @throws IOException 업로드 중 발생한 예외
     */
    GCSResponse getGcsService(GCSRequest request) throws IOException;
}
