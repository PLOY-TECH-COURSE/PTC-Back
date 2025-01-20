package org.plteco.ploytechcourse.api.document.write;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteServiceImpl;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentWriteResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DocumentWriteController {
    DocumentWriteServiceImpl documentWriteService;

    @PostMapping("/Documents")
    public ResponseEntity<String> write(DocumentWriteRequestDto documentWriteRequestDto) {
        DocumentWriteResponseDto response = documentWriteService.writeDocument(documentWriteRequestDto);
        return ResponseEntity.status(200).body(response.getMessage());
    }
}
