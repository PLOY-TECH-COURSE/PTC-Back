package org.plteco.ploytechcourse.api.document;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.application.document.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/documents")
    public ResponseEntity<String> writeDocument(
            @RequestBody @Valid DocumentWriteRequestDTO documentWriteRequestDto
    ) {
        documentService.writeDocument(documentWriteRequestDto);

        return ResponseEntity.status(200).body("글쓰기에 성공하였습니다.");
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentsGetResponseDTO>> getDocument(
            @RequestParam("start") Long start
    ) {
        if(start < 0) throw new IllegalArgumentException("start(글 조회 시작점)가 잘못되었습니다.");

        List<DocumentsGetResponseDTO> result = documentService.getDocuments(start);

        return ResponseEntity.status(200).body(result);
    }

   /* @GetMapping("/documents/{document-id}")
    public ResponseEntity<> getDocumentDetail(
            @RequestParam("document-id") Long documentId
    ) {

    }*/
}
