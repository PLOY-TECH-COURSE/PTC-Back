package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Document writeDocument(User user, DocumentWriteRequestDTO writeRequest);
    List<Document> getDocuments(Long start, Long end);
    List<Document> getDocumentsByUserId(String userId);
    Document getDocument(Long documentId);
    User getDocumentUser(Long documentId);
    Document updateDocument(User user, DocumentUpdateRequestDTO updateRequest);
    Optional<Long> getUserGeneration(Document document);
    void deleteDocument(Long documentId, User user);
    Page<Document> searchDocument(String query, Pageable pageable, SortMethod sortMethod);
}
