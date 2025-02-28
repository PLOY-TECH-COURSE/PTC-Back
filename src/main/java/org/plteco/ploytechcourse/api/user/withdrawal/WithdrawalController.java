package org.plteco.ploytechcourse.api.user.withdrawal;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.user.withdrawal.service.WithdrawalApplicationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/withdrawal")
@Tag(name = "withdrawal-controller : 허동운")
public class WithdrawalController {
    private final WithdrawalApplicationService withdrawalApplicationService;
    @DeleteMapping
    public void withdraw(){
        withdrawalApplicationService.withdraw();
    }
}
