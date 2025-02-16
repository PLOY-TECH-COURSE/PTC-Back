package org.plteco.ploytechcourse.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.plteco.ploytechcourse.api.document.DocumentController;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // json으로 변환

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImVtYWlsIjoicGxveXRlY2hjb3Vyc2VAZ21haWwuY29tIiwidWlkIjoicGxveXRlY2hjb3Vyc2UiLCJyb2xlIjoiUk9MRV9TVVBFUkFETUlOIiwiaWF0IjoxNzM5NDI3MTM0LCJleHAiOjE3Mzk0Mjg5MzR9.Q4gbEoEgfGV5HHsXYIcl3vAVkB9LM1sIAPtO7ag53t4";

    @DisplayName("글 제목, 글 내용 유효성 검사")
    @Test
    void titleAndContentError() throws Exception {
        // given
        DocumentWriteRequestDTO request = DocumentWriteRequestDTO.builder()
                .categoryId(1L)
                .thumbnail("http://example.com")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + accessToken)
        );

        // then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("글 작성에서 글 제목과 글 내용을 필수항목입니다."))
                .andDo(print());
    }
}
