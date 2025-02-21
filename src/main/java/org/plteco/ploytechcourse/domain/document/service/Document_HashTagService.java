package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Document_HashTagService {
    void deleteAllMappingForDocument(Document document);
    void mapDocumentToHashTags(Document document, List<HashTag> hashTags);
    List<HashTag> getHashTagsForDocument(Document document);
    Page<Document> searchDocument(String hashTag, Pageable pageable, SortMethod sortMethod);
}