package org.plteco.ploytechcourse.domain.jwt.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.jwt.model.entity.RefreshToken;
import org.plteco.ploytechcourse.domain.jwt.repository.RefreshRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Transactional
public class AddRefreshEntity {

    private final RefreshRepository refreshRepository;

    /**
     * 리프레시 토큰 엔티티를 데이터베이스에 저장하는 메서드입니다.
     *
     * @param uid 사용자 UID
     * @param email 사용자 이메일
     * @param refresh 리프레시 토큰 값
     * @param expiredMs 리프레시 토큰의 만료 시간 (밀리초)
     */
    public void addRefreshEntity(String uid, String email, String refresh, Long expiredMs) {
        Date createdAt = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(System.currentTimeMillis() + expiredMs);
        refreshRepository.save(
                RefreshToken.builder()
                        .uid(uid)
                        .email(email)
                        .token(refresh)
                        .createdAt(createdAt)
                        .expiresAt(expiresAt)
                        .build()
        );
    }
}
