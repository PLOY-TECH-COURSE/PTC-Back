package org.plteco.ploytechcourse.application.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;

import java.util.List;

public interface DocumentService {
    void writeDocument(DocumentWriteRequestDTO documentWriteRequestDto);
    List<DocumentsGetResponseDTO> getDocuments(Long start);
}
