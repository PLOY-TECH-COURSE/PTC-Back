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

    // like 컬럼 추가 되면 ORDER BY dh.document.like DESC 이거 붙여주세요
    @Query("SELECT dh.document FROM Document_HashTag dh WHERE dh.hashtag.name LIKE CONCAT('%', :hashTag, '%')")
    Page<Document> searchAllByHashTagOrderByLike(@Param("hashTag") String hashTag, Pageable pageable);

    @Query("SELECT dh.document FROM Document_HashTag dh WHERE dh.hashtag.name LIKE CONCAT('%', :hashTag, '%')  ORDER BY dh.document.createAt DESC")
    Page<Document> searchAllByHashTagOrderByCreateAt(@Param("hashTag") String hashTag, Pageable pageable);
}
