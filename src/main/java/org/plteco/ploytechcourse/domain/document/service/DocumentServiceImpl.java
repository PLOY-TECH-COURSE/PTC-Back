package org.plteco.ploytechcourse.domain.document.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final Document_HashTagService documentHashTagService;
    private final DocumentRepository documentRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public Document writeDocument(User user, DocumentWriteRequestDTO writeRequest) {
        Document document = Document.from(user, writeRequest);
        return documentRepository.save(document);
    }

    @Override
    public List<Document> getDocuments(Long start, Long end) {
        return documentRepository.findWithPagination(start, end);
    }

    @Override
    public List<Document> getDocumentsByUserId(String userId) {
        return documentRepository.findByUserUid(userId);
    }

    @Override
    @Transactional
    public Document getDocument(Long documentId) {
        return documentRepository.findById(documentId).orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
    }

    @Override
    public User getDocumentUser(Long documentId) {
        return getDocument(documentId).getUser();
    }

    @Override
    public Document updateDocument(User user, DocumentUpdateRequestDTO updateRequest) {
        Document document = getDocument(updateRequest.documentId());
        if(!document.getUser().equals(user)) throw new IllegalArgumentException("글 작성자만 수정할 수 있습니다.");

        Document newDocument = Document.from(document, updateRequest);
        return documentRepository.save(newDocument);
    }

    @Override
    public Optional<Integer> getUserGeneration(Document document) {
        Long userId =document.getUser().getId();
        return studentRepository.findTechCourseIdByUserId(userId);
    }
    @Override
    public void deleteDocument(Long documentId, User user) {
        documentRepository.findUserById(documentId)
                .filter(writer -> writer.equals(user))
                .ifPresentOrElse(
                        writer -> {
                            Document document = getDocument(documentId);
                            documentHashTagService.deleteAllMappingForDocument(document);
                            documentRepository.deleteById(documentId);
                        },
                        () -> {
                            throw new IllegalArgumentException("작성자를 찾을 수 없거나 글 삭제는 작성자만 할 수 있습니다.");
                        }
                );
    }

    @Override
    public List<Document> searchDocument(String query, Long start, Long size, SortMethod sortMethod) {
        return switch (sortMethod) {
            case CREATE_AT -> documentRepository.findByTitleContainingOrderByIdDescWithPagination(query, start, size);
            case LIKE -> documentRepository.findByTitleContainingOrderByDocumentLikeCountDescWithPagination(query, start, size);
        };
    }
}
