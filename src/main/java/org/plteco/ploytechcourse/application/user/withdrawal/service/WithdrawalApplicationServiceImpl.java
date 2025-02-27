package org.plteco.ploytechcourse.application.user.withdrawal.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.user.withdrawal.service.Withdrawal;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WithdrawalApplicationServiceImpl implements WithdrawalApplicationService {

    private final Withdrawal withdrawal;
    private final UserContextUtil userContextUtil;

    @Override
    public void withdraw() {
        withdrawal.withdraw(userContextUtil.getId());
    }
}
