package org.plteco.ploytechcourse.domain.user.withdrawal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalImpl implements Withdrawal {

    private final UserRepository userRepository;


    @Override
    public void withdraw(Long id) {
        userRepository.deleteById(id);
    }
}
