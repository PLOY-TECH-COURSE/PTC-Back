package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.Document_HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Document_HashTagRepository extends JpaRepository<Document_HashTag, Long> {
    List<Document_HashTag> findAllByDocument(Document document);

    void deleteAllByDocument(Document document);

    @Query("SELECT dh.document FROM Document_HashTag dh WHERE dh.hashtag.name LIKE CONCAT('%', :hashTag, '%') ORDER BY dh.document.documentLikeCount DESC")
    Page<Document> searchAllByHashTagOrderByLike(@Param("hashTag") String hashTag, Pageable pageable);

    @Query("SELECT dh.document FROM Document_HashTag dh WHERE dh.hashtag.name LIKE CONCAT('%', :hashTag, '%')  ORDER BY dh.document.createAt DESC")
    Page<Document> searchAllByHashTagOrderByCreateAt(@Param("hashTag") String hashTag, Pageable pageable);
}
