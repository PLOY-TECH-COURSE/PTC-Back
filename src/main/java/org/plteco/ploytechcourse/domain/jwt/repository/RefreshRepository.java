package org.plteco.ploytechcourse.domain.jwt.repository;

import org.plteco.ploytechcourse.domain.jwt.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * RefreshRepository 인터페이스는 RefreshToken 엔티티에 대한 CRUD 작업을 제공하는 JPA 레포지토리입니다.
 * <p>
 * 이 인터페이스는 Spring Data JPA를 사용하여, 데이터베이스와의 상호작용을 쉽게 처리합니다.
 * </p>
 */
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * 주어진 토큰이 이미 데이터베이스에 존재하는지 확인합니다.
     *
     * @param token 리프레시 토큰
     * @return 토큰이 존재하면 true, 그렇지 않으면 false
     */
    Boolean existsByToken(String token);

    /**
     * 주어진 토큰을 데이터베이스에서 삭제합니다.
     *
     * @param token 삭제할 리프레시 토큰
     */
    @Transactional // 트랜잭션이 적용된 메서드로, 데이터베이스 변경 사항이 커밋되도록 보장
    void deleteByToken(String token);

    /**
     * 주어진 토큰에 해당하는 리프레시 토큰을 조회합니다.
     *
     * @param token 리프레시 토큰
     * @return 리프레시 토큰
     */
    RefreshToken findByToken(String token);
}
