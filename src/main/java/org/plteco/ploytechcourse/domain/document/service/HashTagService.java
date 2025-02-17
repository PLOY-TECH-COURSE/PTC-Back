package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.HashTag;

import java.util.List;

public interface HashTagService {
    List<HashTag> addHashTag(List<String> hashTags);
}
