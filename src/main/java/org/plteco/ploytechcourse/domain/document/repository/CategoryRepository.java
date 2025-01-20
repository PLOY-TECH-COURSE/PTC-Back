package org.plteco.ploytechcourse.domain.document.repository;

import org.plteco.ploytechcourse.domain.document.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}