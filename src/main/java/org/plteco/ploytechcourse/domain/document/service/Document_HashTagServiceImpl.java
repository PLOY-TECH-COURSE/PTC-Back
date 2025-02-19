package org.plteco.ploytechcourse.domain.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.Document_HashTag;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.repository.Document_HashTagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Document_HashTagServiceImpl implements Document_HashTagService {
    private final Document_HashTagRepository documentHashTagRepository;

    @Override
    public void deleteMapping(Document document) {
        documentHashTagRepository.deleteAllByDocument(document);
    }

    @Override
    public void mapping(Document document, List<HashTag> hashTags) {
        hashTags.stream()
                .map(hashTag -> Document_HashTag.builder()
                        .document(document)
                        .hashtag(hashTag)
                        .build()
                )
                .forEach(documentHashTagRepository::save);
    }

    @Override
    public List<HashTag> getHashTagsForDocument(Document document) {
        return documentHashTagRepository.findAllByDocument(document).stream()
                .map(Document_HashTag::getHashtag)
                .toList();
    }

    @Override
    public Page<Document> searchDocument(HashTag hashTag, Pageable pageable) {
        return documentHashTagRepository.searchAllByHashTag(hashTag.getName(), pageable);
    }
}
