package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;

public interface DocumentService {
    Document writeDocument(User user, DocumentWriteRequestDTO writeRequest);
    List<Document> getDocuments(Long start, Long end);
    DocumentInfoDTO getDocumentDetail(Long documentId);
    DocumentUserInfoDTO getDocumentUserInfo(Long documentId);
}
