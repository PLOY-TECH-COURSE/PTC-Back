package org.plteco.ploytechcourse.domain.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.repository.HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;

    @Override
    public List<HashTag> addHashTag(List<String> hashTags) {
        return hashTags.stream()
                .map(hashTag -> HashTag.builder().name(hashTag).build())
                .map(hashTagRepository::save)
                .toList();
    }
}
