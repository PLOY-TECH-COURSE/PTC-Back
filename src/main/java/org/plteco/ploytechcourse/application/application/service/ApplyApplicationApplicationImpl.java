package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.service.ApplyApplicationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyApplicationApplicationImpl implements ApplyApplicationApplication {

    private final ApplyApplicationService applyApplicationService;
    private final UserContextUtil userContextUtil;

    @Override
    public void applyApplication(ApplyApplicationDto applyApplicationDto) {
        log.info("테크코스 신청이 시작되었습니다.");

        if (!applyApplicationService.isValidDuplicationStudent(userContextUtil.getId())) {
            log.error("중복된 신청 시도: 사용자 ID - {}", userContextUtil.getId());
            throw new PltecoException("중복된 신청입니다..", HttpStatus.BAD_REQUEST);
        }

        applyApplicationService.apply(applyApplicationDto);
        log.info("테크코스 신청이 완료되었습니다. 사용자 ID - {}", userContextUtil.getId());
    }
}
