package org.plteco.ploytechcourse.domain.lesson.repository;

import org.plteco.ploytechcourse.domain.lesson.data.model.ClassEntity;
import org.plteco.ploytechcourse.domain.lesson.data.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findByTrackId(Optional<Track> trackId);
}
