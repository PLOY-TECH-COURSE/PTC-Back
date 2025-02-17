package org.plteco.ploytechcourse.domain.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.repository.ApplicationRepository;
import org.plteco.ploytechcourse.domain.application.repository.StudentRepository;
import org.plteco.ploytechcourse.domain.lesson.repository.Tech_courseRepository;
import org.plteco.ploytechcourse.domain.lesson.repository.TrackRepository;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AcceptApplicationServiceImpl implements AcceptApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final Tech_courseRepository techCourseRepository;
    private final TrackRepository trackRepository;

    @Override
    public void acceptApplication(Long id,Long generation,Long track) {
        studentRepository.save(
                Student.builder()
                        .user(userRepository.findById(id).orElse(null))
                        .techCourse(techCourseRepository.findByGeneration(generation))
                        .track(trackRepository.findById(id).orElse(null))
                        .build()
        );
    }

    @Override
    public boolean isApplicationAccepted(Long id) {
        return applicationRepository.findById(id).isPresent();
    }

    @Override
    public boolean isDuplicationApplicationAccepted(Long id) {
        return studentRepository.findById(id).isPresent();
    }

}
