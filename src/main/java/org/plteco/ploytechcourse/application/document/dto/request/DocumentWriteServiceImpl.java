package org.plteco.ploytechcourse.application.document.dto.request;

import org.plteco.ploytechcourse.application.document.dto.response.DocumentWriteResponseDto;
import org.plteco.ploytechcourse.application.document.service.DocumentWriteService;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentWriteServiceImpl implements DocumentWriteService {

    @Override
    public DocumentWriteResponseDto writeDocument(DocumentWriteRequestDto documentWriteRequestDto) {
        Document document = new Document();
        return null;
    }
}