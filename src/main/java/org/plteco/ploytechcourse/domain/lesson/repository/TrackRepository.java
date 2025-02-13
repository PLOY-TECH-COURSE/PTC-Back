package org.plteco.ploytechcourse.domain.lesson.repository;

import org.plteco.ploytechcourse.domain.lesson.data.model.Track;
import org.plteco.ploytechcourse.domain.lesson.data.model.Tech_course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByGeneration(Tech_course genertaion);
}
