package org.plteco.ploytechcourse.api.user.withdrawal;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.withdrawal.service.WithdrawalApplicationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/withdrawal")
public class WithdrawalController {
    private final WithdrawalApplicationService withdrawalApplicationService;
    @DeleteMapping
    public void withdraw(){
        withdrawalApplicationService.withdraw();
    }
}
