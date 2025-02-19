package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Document_HashTagService {
    void deleteMapping(Document document);
    void mapping(Document document, List<HashTag> hashTags);
    List<HashTag> getHashTagsForDocument(Document document);
    Page<Document> searchDocument(HashTag hashTag, Pageable pageable, String sortMethod);
}