package org.plteco.ploytechcourse.domain.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.Document_HashTag;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.domain.document.repository.Document_HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Document_HashTagServiceImpl implements Document_HashTagService {
    private final Document_HashTagRepository documentHashTagRepository;
    // 입력값 검증하는 로직 필요...

    @Override
    public void deleteAllMappingForDocument(Document document) {
        documentHashTagRepository.deleteAllByDocument(document);
    }

    @Override
    public void mapDocumentToHashTags(Document document, List<HashTag> hashTags) {
        List<Document_HashTag> mappings = hashTags.stream()
                .map(hashTag -> Document_HashTag.builder()
                        .document(document)
                        .hashtag(hashTag)
                        .build())
                .toList();
        documentHashTagRepository.saveAll(mappings);
    }

    @Override
    public List<HashTag> getHashTagsForDocument(Document document) {
        return documentHashTagRepository.findAllByDocument(document).stream()
                .map(Document_HashTag::getHashtag)
                .toList();
    }

    @Override
    public List<Document> searchDocument(String hashTag, Long start, Long size, SortMethod sortMethod) {
        return switch (sortMethod) {
            case CREATE_AT -> documentHashTagRepository.searchAllByHashTagOrderByCreateAtWithPagination(hashTag, start, size);
            case LIKE -> documentHashTagRepository.searchAllByHashTagOrderByLikeWithPagination(hashTag, start, size);
        };
    }
}
