package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.domain.application.service.ApplyApplicationService;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyApplicationApplicationImpl implements ApplyApplicationApplication {

    private final ApplyApplicationService applyApplicationService;
    private final UserContextUtil userContextUtil;

    @Override
    public String applyApplication(ApplyApplicationDto applyApplicationDto) {
        if(applyApplicationService.isValidDuplicationStudent(userContextUtil.getId())){
            return "중복된 요청입니다";
        }
        if(applyApplicationService.apply(applyApplicationDto)==null){
            return "저장 안됨";
        }
        return "저장 잘됨";
    }
}
