package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.DocumentComment;
import org.plteco.ploytechcourse.domain.document.model.DocumentCommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentCommentRepository extends JpaRepository<DocumentComment, DocumentCommentId> {
}
