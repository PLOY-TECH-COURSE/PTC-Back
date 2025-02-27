package org.plteco.ploytechcourse.application.application.service;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.application.dto.AcceptApplicationDto;
import org.plteco.ploytechcourse.domain.application.service.AcceptApplicationService;
import org.plteco.ploytechcourse.domain.application.service.DeclineApplicationService;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptApplicationImpl implements AcceptApplication {

    private final AcceptApplicationService acceptApplicationService;
    private final DeclineApplicationService declineApplicationService;

    @Override
    public void accept(AcceptApplicationDto acceptApplicationDto) {
            if(acceptApplicationService.isApplicationAccepted(acceptApplicationDto.getUserId())){
                throw new PltecoException("신청안한 유저인데요", HttpStatus.BAD_REQUEST);
            }
            else if(acceptApplicationService.isDuplicationApplicationAccepted(acceptApplicationDto.getUserId())){
                throw new PltecoException("중복된 유저입니다.", HttpStatus.BAD_REQUEST);
            }
            acceptApplicationService.acceptApplication(
                    acceptApplicationDto.getUserId(),
                    acceptApplicationDto.getGeneration(),
                    acceptApplicationDto.getTrackId()
                    );
    }

    @Override
    public void decline(Long id) {
        declineApplicationService.declineApplication(id);
    }
}
