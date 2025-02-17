package org.plteco.ploytechcourse.domain.application.service;

public interface AcceptApplicationService {
    void acceptApplication(Long id,Long generation,Long track);
    boolean isApplicationAccepted(Long id);
    boolean isDuplicationApplicationAccepted(Long id);
}
