package org.plteco.ploytechcourse.domain.document.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final StudentRepository studentRepository;
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    @Override
    public Document writeDocument(User user, DocumentWriteRequestDTO writeRequest) {
        Document document = Document.builder()
                .user(user)
                .title(writeRequest.title())
                .content(writeRequest.content())
                .thumbnail(Optional.ofNullable(writeRequest.thumbnail()).orElse("기본 썸네일 이미지"))
                .introduction(writeRequest.introduction())
                .createAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();

        return documentRepository.save(document);
    }

    @Override
    public List<Document> getDocuments(Long start, Long end) {
        return documentRepository.findWithPagination(start, end);
    }

    @Override
    public DocumentInfoDTO getDocumentDetail(Long documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
        return modelMapper.map(document, DocumentInfoDTO.class);
    }

    @Override
    public DocumentUserInfoDTO getDocumentUserInfo(Long documentId) {
        User user = documentRepository.findById(documentId).orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다.")).getUser();
        return modelMapper.map(user, DocumentUserInfoDTO.class);
    }

    @Override
    public Document updateDocument(User user, DocumentUpdateRequestDTO updateRequest) {
        Document document = documentRepository.findById(updateRequest.documentId()).orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
        if(!document.getUser().equals(user)) throw new IllegalArgumentException("글 작성자만 수정할 수 있습니다.");

        Document newDocument = Document.from(document, updateRequest);
        return documentRepository.save(newDocument);
    }

    @Override
    public Long getUserGeneration(Document document) {
        Long userId =document.getUser().getId();
        return studentRepository.findTechCourseIdByUserId(userId).orElse(null);
    }
    @Override
    public void deleteDocument(Long documentId) {
        documentRepository.findUserById(documentId)
                .filter(writer -> writer.equals(userContextUtil.getCurrentUser()))
                .ifPresentOrElse(
                        writer -> documentRepository.deleteById(documentId),
                        () -> {
                            throw new IllegalArgumentException("작성자를 찾을 수 없거나 글 삭제는 작성자만 할 수 있습니다.");
                        }
                );
    }

    @Override
    public Page<Document> searchDocument(String query, Pageable pageable, String sortMethod) {
        if(sortMethod.equals("create_at"))
            return documentRepository.searchAllByTitleLikeOrderByLike(query, pageable);
        return documentRepository.searchAllByTitleLikeOrderByLike(query, pageable);
    }
}
