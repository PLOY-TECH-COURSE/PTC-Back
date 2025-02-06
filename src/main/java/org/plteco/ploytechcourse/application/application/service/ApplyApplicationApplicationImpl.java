package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.service.ApplyApplicationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyApplicationApplicationImpl implements ApplyApplicationApplication {

    private final ApplyApplicationService applyApplicationService;
    private final UserContextUtil userContextUtil;

    @Override
    public void applyApplication(ApplyApplicationDto applyApplicationDto) {
        if(!applyApplicationService.isValidDuplicationStudent(userContextUtil.getId())){
            throw new PltecoException("중복된 신청입니다..", HttpStatus.BAD_REQUEST);
        }
        applyApplicationService.apply(applyApplicationDto);
        throw new PltecoException("테크코스 신청이 완료되었습니다.", HttpStatus.OK);
    }
}
