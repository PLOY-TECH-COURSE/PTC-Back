package org.plteco.ploytechcourse.application.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GCSRequest {
    private MultipartFile file;
}
