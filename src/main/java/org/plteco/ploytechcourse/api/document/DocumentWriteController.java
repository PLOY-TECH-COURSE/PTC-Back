package org.plteco.ploytechcourse.api.document;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;
import org.plteco.ploytechcourse.application.document.service.DocumentWriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentWriteController {
    DocumentWriteService documentWriteService;

    @PostMapping("/documents")
    public ResponseEntity<String> writeDocument(
            @RequestBody @Valid DocumentWriteRequestDto documentWriteRequestDto
    ) {
        documentWriteService.writeDocument(documentWriteRequestDto);

        return ResponseEntity.status(200).body("글쓰기에 성공하였습니다.");
    }
}
