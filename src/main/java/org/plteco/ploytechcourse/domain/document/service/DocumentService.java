package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Document writeDocument(User user, DocumentWriteRequestDTO writeRequest);
    List<Document> getDocuments(Long start, Long end);
    List<Document> getDocumentsByUserId(String userId);
    Document getDocument(Long documentId);
    User getDocumentUser(Long documentId);
    Document updateDocument(User user, DocumentUpdateRequestDTO updateRequest);
    Optional<Integer> getUserGeneration(Document document);
    void deleteDocument(Long documentId, User user);
    List<Document> searchDocument(String query, Long start, Long size, SortMethod sortMethod);
}
