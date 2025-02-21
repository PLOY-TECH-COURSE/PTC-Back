package org.plteco.ploytechcourse.domain.document.service;

import org.plteco.ploytechcourse.domain.document.model.HashTag;

import java.util.List;
import java.util.Optional;

public interface HashTagService {
    List<HashTag> addHashTag(List<String> hashTags);
    Optional<HashTag> findHashTag(String hashStr);
}
