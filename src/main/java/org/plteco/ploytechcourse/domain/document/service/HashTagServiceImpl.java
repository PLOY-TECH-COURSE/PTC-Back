package org.plteco.ploytechcourse.domain.document.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.repository.HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;

    @Override
    public List<HashTag> addHashTag(List<String> hashTags) {
        /* 나중에 성능 문제 생기면 사용
        // 한 번에 모든 태그 조회
        Map<String, HashTag> existingHashTags = hashTagRepository.findByNameIn(hashTags).stream()
                .collect(Collectors.toMap(HashTag::getName, hashTag -> hashTag));

        // 새 태그만 추가
        List<HashTag> newHashTags = hashTags.stream()
                .filter(tag -> !existingHashTags.containsKey(tag)) // 이미 존재하는 태그는 제외
                .map(tag -> HashTag.builder().name(tag).build())
                .collect(Collectors.toList());

        // 새 태그 저장
        if (!newHashTags.isEmpty()) {
            hashTagRepository.saveAll(newHashTags);
        }

        // 기존 태그 + 새로 저장된 태그 반환
        newHashTags.forEach(tag -> existingHashTags.put(tag.getName(), tag));
        return new ArrayList<>(existingHashTags.values());
        */
        return hashTags.stream()
                .map(hashTag -> findHashTag(hashTag)
                        .orElseGet(() -> hashTagRepository.save(HashTag.builder().name(hashTag).build()))) // Lazy Evaluation
                .toList();
    }

    @Override
    public Optional<HashTag> findHashTag(String hashStr) {
        return hashTagRepository.findByName(hashStr);
    }
}
