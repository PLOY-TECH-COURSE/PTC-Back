package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;

import java.util.List;

public interface Document_HashTagService {
    void mapping(Document document, List<HashTag> hashTags);
    List<HashTag> getHashTagsForDocument(Document document);
}
