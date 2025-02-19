package org.plteco.ploytechcourse.application.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;

import java.util.List;

public interface DocumentServiceApplication {
    void writeDocument(DocumentWriteRequestDTO documentWriteRequestDto);
    List<DocumentsGetResponseDTO> getDocuments(Long start);
    DocumentDetailGetResponseDTO getDocumentDetail(Long documentId);
    void updateDocument(DocumentUpdateRequestDTO documentUpdateRequestDto);
    void deleteDocument(Long documentId);
    List<DocumentsGetResponseDTO> searchDocument(String title, String sortMethod, int start);
}
