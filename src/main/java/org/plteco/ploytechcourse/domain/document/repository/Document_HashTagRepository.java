package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.Document_HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Document_HashTagRepository extends JpaRepository<Document_HashTag, Long> {
    List<Document_HashTag> findAllByDocument(Document document);
    void deleteAllByDocument(Document document);
}
