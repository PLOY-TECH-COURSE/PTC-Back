package org.plteco.ploytechcourse.application.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;

public interface DocumentWriteService {
    void writeDocument(DocumentWriteRequestDto documentWriteRequestDto);
}
