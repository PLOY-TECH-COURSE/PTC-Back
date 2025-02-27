package org.plteco.ploytechcourse.domain.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.application.repository.ApplicationRepository;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeclineApplicationServiceImpl implements DeclineApplicationService {

    private final ApplicationRepository applicationRepository;


    @Override
    public void declineApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
