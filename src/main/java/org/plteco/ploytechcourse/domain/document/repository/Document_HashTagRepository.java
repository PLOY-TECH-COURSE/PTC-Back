package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.Document_HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Document_HashTagRepository extends JpaRepository<Document_HashTag, Long> {
    List<Document_HashTag> findAllByDocument(Document document);

    void deleteAllByDocument(Document document);

    @Query(value = "SELECT d.* FROM Document_HashTag dh " +
            "JOIN Document d ON dh.document_id = d.id " +
            "JOIN HashTag h ON dh.hashtag_id = h.id " +
            "WHERE h.name LIKE CONCAT('%', :hashTag, '%') " +
            "ORDER BY d.document_like_count DESC LIMIT :start, :size", nativeQuery = true)
    List<Document> searchAllByHashTagOrderByLikeWithPagination(@Param("hashTag") String hashTag, @Param("start") Long start, @Param("size") Long size);

    @Query(value = "SELECT d.* FROM Document_HashTag dh " +
            "JOIN Document d ON dh.document_id = d.id " +
            "JOIN HashTag h ON dh.hashtag_id = h.id " +
            "WHERE h.name LIKE CONCAT('%', :hashTag, '%') " +
            "ORDER BY d.id DESC LIMIT :start, :size", nativeQuery = true)
    List<Document> searchAllByHashTagOrderByCreateAtWithPagination(@Param("hashTag") String hashTag, @Param("start") Long start, @Param("size") Long size);
}
