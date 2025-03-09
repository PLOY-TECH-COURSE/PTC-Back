package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;

import java.util.List;

public interface Document_HashTagService {
    void deleteAllMappingForDocument(Document document);
    void mapDocumentToHashTags(Document document, List<HashTag> hashTags);
    List<HashTag> getHashTagsForDocument(Document document);
    List<Document> searchDocument(String hashTag, Long start, Long size, SortMethod sortMethod);
}