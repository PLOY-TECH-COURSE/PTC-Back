package org.plteco.ploytechcourse.domain.lesson.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.lesson.dto.ClassDto;
import org.plteco.ploytechcourse.application.lesson.dto.TechCourseDto;
import org.plteco.ploytechcourse.application.lesson.dto.TrackDto;
import org.plteco.ploytechcourse.domain.lesson.data.model.ClassEntity;
import org.plteco.ploytechcourse.domain.lesson.data.model.Tech_course;
import org.plteco.ploytechcourse.domain.lesson.data.model.Track;
import org.plteco.ploytechcourse.domain.lesson.repository.ClassRepository;
import org.plteco.ploytechcourse.domain.lesson.repository.Tech_courseRepository;
import org.plteco.ploytechcourse.domain.lesson.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final Tech_courseRepository techCourseRepository;
    private final TrackRepository trackRepository;
    private final ModelMapper modelMapper;

    @Override
    public void SavetechCourse(TechCourseDto techCourseDto) {
        Tech_course techCourse = Tech_course.builder()
                .name(techCourseDto.getName())
                .explanation(techCourseDto.getExplanation())
                .build();
        techCourseRepository.save(techCourse);
    }

    @Override
    public List<TechCourseDto> ShowTechCourse() {
        List<Tech_course> techCourseList = techCourseRepository.findAll();

        return techCourseList.stream()
                .map(techCourse -> modelMapper.map(techCourse, TechCourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void Savetrack(TrackDto trackDto) {
        Tech_course techCourse = techCourseRepository.findByGeneration(trackDto.getGeneration()); // 수정된 부분
        Track track = Track.builder()
                .generation(techCourse) // 수정된 부분
                .name(trackDto.getName())
                .explanation(trackDto.getExplanation())
                .build();
        trackRepository.save(track);
    }

    @Override
    public List<TrackDto> ShowTrack() {
        List<Track> trackList = trackRepository.findAll();
        return trackList.stream()
                .map(track -> modelMapper.map(track, TrackDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> getTrack(Long generation) {
        List<Track> trackList = trackRepository.findByGeneration(techCourseRepository.findByGeneration(generation));
        return trackList.stream()
                .map(track -> modelMapper.map(track, TrackDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void Saveclass(ClassDto classDto) {
        ClassEntity classEntity = ClassEntity.builder()
                .trackId(trackRepository.findById(classDto.getTrackId()).orElse(null))
                .name(classDto.getName())
                .content(classDto.getContent())
                .thumbnail(classDto.getThumbnail())
                .explanation(classDto.getExplanation())
                .build();
        classRepository.save(classEntity);
    }

    @Override
    public List<ClassDto> ShowClass() {
        List<ClassEntity> classList = classRepository.findAll();
        return classList.stream()
                .map(classEntity -> modelMapper.map(classEntity, ClassDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDto> getClass(Long id) {
        List<ClassEntity> classList = classRepository.findByTrackId(trackRepository.findById(id));
        return classList.stream()
                .map(classEntity -> modelMapper.map(classEntity, ClassDto.class))
                .collect(Collectors.toList());
    }
}
