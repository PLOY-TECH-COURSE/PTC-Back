package org.plteco.ploytechcourse.application.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentWriteResponseDto;

public interface DocumentWriteService {
    DocumentWriteResponseDto writeDocument(DocumentWriteRequestDto documentWriteRequestDto);
}
