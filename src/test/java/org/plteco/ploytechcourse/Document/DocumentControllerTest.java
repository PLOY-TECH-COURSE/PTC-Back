package org.plteco.ploytechcourse.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.plteco.ploytechcourse.api.document.write.DocumentWriteController;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDto;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteServiceImpl;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentWriteResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 웹 레이어만 로드 (서비스, 레포지토리는 X 따라서 속도를 높힘)
// MockMvc 객체를 통해 HTTP 요청과 응답을 시물레이션하여 테스트 할 수 있습니다.
// DocumentController 클래스만 로드
@WebMvcTest(DocumentWriteController.class)   //  컨트롤러 레이어만 검증
public class DocumentControllerTest {

    // 테스트를 하기 위해 HTTP 요청을 보내지 않고 컨트롤러를 호출하는 객체
    @Autowired
    private MockMvc mockMvc;

    @Mock   // 의존성 객체를 가짜(Mock) 객체로 생성 (MockBean 이제 사용못해서 이 방법으로 해야함)
    private DocumentWriteServiceImpl documentService;

    @InjectMocks    // Mock으로 지정된 의존성 객체 주입
    private DocumentWriteController documentWriteController;

    @Test
    @DisplayName("글 작성 컨트롤러 테스트")
    public void documentWrite() throws Exception {
        // Given
        DocumentWriteRequestDto post = DocumentWriteRequestDto.builder()
                .title("플테코 첫 수업")
                .content("오늘은 html을 배웠다.....")
                .thumbnail("https://velog.velcdn.com/images/huhon/post/5c510375-11a6-4fcf-864d-70db704c667f/image.png")
                .introduction("소마고 선배님들께 배운 재미난 html이야기")
                .categoryId(1L)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(post);

        DocumentWriteResponseDto response = DocumentWriteResponseDto.builder()
                .message("글쓰기에 성공하였습니다.")
                .build();

        given(documentService.writeDocument(Mockito.any(DocumentWriteRequestDto.class))).willReturn(DocumentWriteResponseDto.builder().message("글쓰기에 성공하였습니다.").build());

        // When
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/Documents")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // Then
        actions
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.message").value("글쓰기에 성공하였습니다."));
    }
}
