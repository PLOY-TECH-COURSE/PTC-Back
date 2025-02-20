package org.plteco.ploytechcourse.application.document.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.service.DocumentService;
import org.plteco.ploytechcourse.domain.document.service.Document_HashTagService;
import org.plteco.ploytechcourse.domain.document.service.HashTagService;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceApplicationImpl implements DocumentServiceApplication {
    private final UserContextUtil userContextUtil;
    private final ModelMapper modelMapper;

    private final DocumentService documentService;
    private final HashTagService hashTagService;
    private final Document_HashTagService documentHashTagService;
    private final DocumentLikeService documentLikeService;
    private final FavoriteService favoriteService;

    @Override
    public void writeDocument(DocumentWriteRequestDTO writeRequest) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentService.writeDocument(user, writeRequest);

        List<HashTag> hashTags = hashTagService.addHashTag(writeRequest.hasTag());

        documentHashTagService.mapping(document, hashTags);
    }

    @Override
    public List<DocumentsGetResponseDTO> getDocuments(Long start) {

        if(start < 0) throw new IllegalArgumentException("start 값이 올바르지 않습니다.");

        List<Document> documents = documentService.getDocuments(start, start + 20);

        return documents.stream()
                .map(document -> {
                    Long like = documentLikeService.getLikes(document.getId());
                    List<String> hashTags = documentHashTagService.getHashTagsForDocument(document).stream()
                            .map(HashTag::getName)
                            .toList();

                    return DocumentsGetResponseDTO.from(document, hashTags, like);
                })
                .toList();
    }

    @Override
    public DocumentDetailGetResponseDTO getDocumentDetail(Long documentId) {
        if(documentId == null || documentId < 1) throw new IllegalArgumentException("document_id(글 id)가 존재하지 않습니다.");

        User user = userContextUtil.getCurrentUser();

        Document document =  documentService.getDocument(documentId);
        DocumentInfoDTO documentInfo =modelMapper.map(document, DocumentInfoDTO.class);

        User writer = documentService.getDocumentUser(documentId);
        DocumentUserInfoDTO userInfo = modelMapper.map(writer, DocumentUserInfoDTO.class);

        List<String> hashTags = documentHashTagService.getHashTagsForDocument(document).stream()
                .map(HashTag::getName)
                .toList();

        Long generation = documentService.getUserGeneration(document);

        Long likes = documentLikeService.getLikes(documentId);
        boolean likeOn = documentLikeService.isLiked(document, user);
        boolean favoriteOn = favoriteService.isFavorite(user, document);

        return new DocumentDetailGetResponseDTO(documentInfo, userInfo, likes, likeOn, favoriteOn, generation, hashTags);
    }

    @Override
    public void updateDocument(DocumentUpdateRequestDTO documentUpdateRequestDto) {
        User user = userContextUtil.getCurrentUser();

        Document document = documentService.updateDocument(user, documentUpdateRequestDto);

        List<HashTag> hashTags = hashTagService.addHashTag(documentUpdateRequestDto.hasTag());

        documentHashTagService.deleteMapping(document);
        documentHashTagService.mapping(document, hashTags);
    }

    @Override
    public void deleteDocument(Long documentId) {
        documentService.deleteDocument(documentId, userContextUtil.getCurrentUser());
    }

    @Override
    public List<DocumentsGetResponseDTO> searchDocument(String query, String sortMethod, int start) {
        Pageable pageable = PageRequest.of(start, 20);

        Page<Document> documents;
        if(query.charAt(0) == '#') {
            HashTag hashTag = hashTagService.toHashTags(query.replace("#", ""));
            documents = documentHashTagService.searchDocument(hashTag, pageable, sortMethod);
        }
        else
            documents = documentService.searchDocument(query, pageable, sortMethod);

        return documents.stream()
                .map(document -> {
                    Long like = documentLikeService.getLikes(document.getId());
                    List<String> hashTags = documentHashTagService.getHashTagsForDocument(document).stream()
                            .map(HashTag::getName)
                            .toList();

                    return DocumentsGetResponseDTO.from(document, hashTags, like);
                })
                .toList();
    }
}