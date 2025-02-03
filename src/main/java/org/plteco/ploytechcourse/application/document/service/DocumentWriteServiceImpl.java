package org.plteco.ploytechcourse.application.document.service;

import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.springframework.stereotype.Service;

@Service
public class DocumentWriteServiceImpl implements DocumentWriteService {

    @Override
    public void writeDocument(DocumentWriteRequestDto documentWriteRequestDto) {
        Document document = new Document();
    }
}