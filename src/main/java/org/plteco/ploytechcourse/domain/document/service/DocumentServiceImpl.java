package org.plteco.ploytechcourse.domain.document.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
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
}
