package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "SELECT * FROM document ORDER BY created_at DESC LIMIT :start, :size", nativeQuery = true)
    List<Document> findWithPagination(@Param("start") Long start, @Param("size") Long size);

    @Query("SELECT COUNT(d) FROM Document d WHERE d.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT d.user FROM Document d WHERE d.id = :documentId")
    Optional<User> findUserById(@Param("documentId") Long documentId);

    // like 추가되면 주석풀어주세요
    // Page<Document> findByTitleContainingOrderByLikeDesc(String query, Pageable pageable);

    Page<Document> findByTitleContainingOrderByCreateAtDesc(String query, Pageable pageable);
}
