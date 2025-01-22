package org.plteco.ploytechcourse.application.application.service;

import org.plteco.ploytechcourse.application.application.dto.ApplyApplicationDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;

public interface ApplyApplicationApplication {
    void applyApplication(ApplyApplicationDto applyApplicationDto);
}
