package org.plteco.ploytechcourse.application.document.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.domain.document.model.Category;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.repository.CategoryRepository;
import org.plteco.ploytechcourse.domain.document.repository.DocumentRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final UserContextUtil userContextUtil;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final CategoryRepository categoryRepository;
    private final DocumentLikeRepository documentLikeRepository;

    @Override
    public void writeDocument(DocumentWriteRequestDTO documentWriteRequestDto) {
        User user = userRepository.findById(userContextUtil.getId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String thumbnail = documentWriteRequestDto.thumbnail().isEmpty() ? "기본 썸네일 이미지" : documentWriteRequestDto.thumbnail();
        Category category = categoryRepository.findById(documentWriteRequestDto.categoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        Document document = Document.builder()
                .user(user)
                .title(documentWriteRequestDto.title())
                .content(documentWriteRequestDto.content())
                .thumbnail(thumbnail)
                .introduction(documentWriteRequestDto.introduction())
                .createAt(LocalDate.now())
                .category(category)
                .build();

        documentRepository.save(document);
    }

    @Override
    public List<DocumentsGetResponseDTO> getDocuments(Long start) {
        List<DocumentsGetResponseDTO> result = new ArrayList<>();

        List<Document> documents = documentRepository.findWithPagination(start, start + 20);

        for(Document document : documents) {
            Long like = documentLikeRepository.countByDocumentId(document.getId());

            DocumentsGetResponseDTO response = new DocumentsGetResponseDTO(
                    document.getId(),
                    document.getTitle(),
                    document.getIntroduction(),
                    document.getThumbnail(),
                    like,
                    document.getUser().getId(),
                    document.getUser().getName(),
                    document.getUser().getProfile(),
                    document.getCreateAt()
            );

            result.add(response);
        }

        return result;
    }
}