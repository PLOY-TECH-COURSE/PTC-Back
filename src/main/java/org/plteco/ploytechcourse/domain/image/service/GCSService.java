package org.plteco.ploytechcourse.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GCSService {
    /**
     * 파일을 Google Cloud Storage에 업로드하고 업로드된 파일의 URL을 반환합니다.
     *
     * @param file 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 업로드 중 발생한 예외
     */
    String uploadObject(MultipartFile file) throws IOException;
}
