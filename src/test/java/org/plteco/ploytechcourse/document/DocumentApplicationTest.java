package org.plteco.ploytechcourse.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.document.dto.DocumentInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.DocumentUserInfoDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.document.dto.response.DocumentsGetResponseDTO;
import org.plteco.ploytechcourse.application.document.service.DocumentServiceApplicationImpl;
import org.plteco.ploytechcourse.domain.document.model.Document;
import org.plteco.ploytechcourse.domain.document.model.HashTag;
import org.plteco.ploytechcourse.domain.document.model.SortMethod;
import org.plteco.ploytechcourse.domain.document.service.DocumentService;
import org.plteco.ploytechcourse.domain.document.service.Document_HashTagService;
import org.plteco.ploytechcourse.domain.document.service.HashTagService;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.domain.like.documentlike.service.DocumentLikeService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentApplicationTest {
    @Mock
    UserContextUtil userContextUtil;

    @Mock
    UserRepository userRepository;

    @Mock
    DocumentService documentService;

    @Mock
    HashTagService hashTagService;

    @Mock
    Document_HashTagService documentHashTagService;

    @Mock
    DocumentLikeService documentLikeService;

    @Mock
    FavoriteService favoriteService;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    DocumentServiceApplicationImpl documentServiceApplication;

    private User testUser;
    private Document testDocument;
    private List<HashTag> testHashTags;
    private DocumentWriteRequestDTO testWriteRequestDTO;
    private DocumentUpdateRequestDTO testUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        // 테스트 사용자 설정
        testUser = User.builder()
                .id(1L)
                .uid("test-user-id")
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .role(RoleEnum.ROLE_USER)
                .grade(1L)
                .classNumber(1L)
                .number(1L)
                .build();

        // 테스트 문서 설정
        testDocument = Document.builder()
                .id(1L)
                .user(testUser)
                .title("Test Document")
                .content("Test Content")
                .thumbnail("https://example.com/thumbnail.jpg")
                .introduction("Test Introduction")
                .createAt(LocalDate.now())
                .documentLikeCount(0L)
                .build();

        // 테스트 해시태그 설정
        testHashTags = Arrays.asList(
                new HashTag(1L, "tag1"),
                new HashTag(2L, "tag2")
        );

        // 테스트 문서 작성 요청 DTO 설정
        testWriteRequestDTO = new DocumentWriteRequestDTO(
                "Test Document",
                "Test Content",
                "https://example.com/thumbnail.jpg",
                "Test Introduction",
                Arrays.asList("tag1", "tag2")
        );

        // 테스트 문서 업데이트 요청 DTO 설정
        testUpdateRequestDTO = new DocumentUpdateRequestDTO(
                1L,
                "Updated Document",
                "Updated Content",
                "https://example.com/updated-thumbnail.jpg",
                "Updated Introduction",
                Arrays.asList("tag1", "tag2", "tag3")
        );
    }

    @Test
    @DisplayName("문서 목록 조회 테스트")
    void getDocumentsTest() {
        // given
        Long start = 0L;
        List<Document> documents = List.of(testDocument);

        when(documentService.getDocuments(start, 21L)).thenReturn(documents);
        when(documentLikeService.getLikes(anyLong())).thenReturn(5L);
        when(documentHashTagService.getHashTagsForDocument(any(Document.class))).thenReturn(testHashTags);

        // when
        List<DocumentsGetResponseDTO> result = documentServiceApplication.getDocuments(start);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDocument.getTitle(), result.get(0).title());
        // Note: DocumentsGetResponseDTO doesn't have a direct content field
        assertEquals(5L, result.get(0).likes());
        assertEquals(2, result.get(0).hashTags().size());

        // verify
        verify(documentService).getDocuments(start, 21L);
        verify(documentLikeService).getLikes(testDocument.getId());
        verify(documentHashTagService).getHashTagsForDocument(testDocument);
    }

    @Test
    @DisplayName("사용자 ID로 문서 목록 조회 테스트")
    void getDocumentsByUserIdTest() {
        // given
        String userId = "test-user-id";
        List<Document> documents = List.of(testDocument);

        when(documentService.getDocumentsByUserId(userId)).thenReturn(documents);
        when(documentLikeService.getLikes(anyLong())).thenReturn(5L);
        when(documentHashTagService.getHashTagsForDocument(any(Document.class))).thenReturn(testHashTags);

        // when
        List<DocumentsGetResponseDTO> result = documentServiceApplication.getDocumentsByUserId(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDocument.getTitle(), result.get(0).title());
        // Note: DocumentsGetResponseDTO doesn't have a direct content field
        assertEquals(5L, result.get(0).likes());
        assertEquals(2, result.get(0).hashTags().size());

        // verify
        verify(documentService).getDocumentsByUserId(userId);
        verify(documentLikeService).getLikes(testDocument.getId());
        verify(documentHashTagService).getHashTagsForDocument(testDocument);
    }

    @Test
    @DisplayName("문서 상세 조회 테스트")
    void getDocumentDetailTest() {
        // given
        Long documentId = 1L;
        DocumentInfoDTO documentInfoDTO = new DocumentInfoDTO();
        DocumentUserInfoDTO userInfoDTO = new DocumentUserInfoDTO();

        when(userContextUtil.getCurrentUser()).thenReturn(testUser);
        when(documentService.getDocument(documentId)).thenReturn(testDocument);
        when(modelMapper.map(testDocument, DocumentInfoDTO.class)).thenReturn(documentInfoDTO);
        when(documentService.getDocumentUser(documentId)).thenReturn(testUser);
        when(modelMapper.map(testUser, DocumentUserInfoDTO.class)).thenReturn(userInfoDTO);
        when(documentHashTagService.getHashTagsForDocument(testDocument)).thenReturn(testHashTags);
        when(documentService.getUserGeneration(testDocument)).thenReturn(Optional.of(1));
        when(documentLikeService.getLikes(documentId)).thenReturn(5L);
        when(documentLikeService.isLiked(testDocument, testUser)).thenReturn(true);
        when(favoriteService.isFavorite(testUser, testDocument)).thenReturn(false);

        // when
        DocumentDetailGetResponseDTO result = documentServiceApplication.getDocumentDetail(documentId);

        // then
        assertNotNull(result);
        assertEquals(documentInfoDTO, result.documentInfoDTO());
        assertEquals(userInfoDTO, result.userInfoDTO());
        assertEquals(5L, result.likes());
        assertTrue(result.like_on());
        assertFalse(result.favorite_on());
        assertEquals("1", result.generation());
        assertEquals(2, result.hash_tag().size());

        // verify
        verify(userContextUtil).getCurrentUser();
        verify(documentService).getDocument(documentId);
        verify(modelMapper).map(testDocument, DocumentInfoDTO.class);
        verify(documentService).getDocumentUser(documentId);
        verify(modelMapper).map(testUser, DocumentUserInfoDTO.class);
        verify(documentHashTagService).getHashTagsForDocument(testDocument);
        verify(documentService).getUserGeneration(testDocument);
        verify(documentLikeService).getLikes(documentId);
        verify(documentLikeService).isLiked(testDocument, testUser);
        verify(favoriteService).isFavorite(testUser, testDocument);
    }

    @Test
    @DisplayName("문서 작성 테스트")
    void writeDocumentTest() {
        // given
        when(userContextUtil.getCurrentUser()).thenReturn(testUser);
        when(documentService.writeDocument(eq(testUser), any(DocumentWriteRequestDTO.class))).thenReturn(testDocument);
        when(hashTagService.addHashTag(anyList())).thenReturn(testHashTags);

        // when
        documentServiceApplication.writeDocument(testWriteRequestDTO);

        // then
        verify(userContextUtil).getCurrentUser();
        verify(documentService).writeDocument(eq(testUser), eq(testWriteRequestDTO));
        verify(hashTagService).addHashTag(eq(testWriteRequestDTO.hasTag()));
        verify(documentHashTagService).mapDocumentToHashTags(eq(testDocument), eq(testHashTags));
    }

    @Test
    @DisplayName("문서 업데이트 테스트")
    void updateDocumentTest() {
        // given
        when(userContextUtil.getCurrentUser()).thenReturn(testUser);
        when(documentService.updateDocument(eq(testUser), any(DocumentUpdateRequestDTO.class))).thenReturn(testDocument);
        when(hashTagService.addHashTag(anyList())).thenReturn(testHashTags);

        // when
        documentServiceApplication.updateDocument(testUpdateRequestDTO);

        // then
        verify(userContextUtil).getCurrentUser();
        verify(documentService).updateDocument(eq(testUser), eq(testUpdateRequestDTO));
        verify(hashTagService).addHashTag(eq(testUpdateRequestDTO.hasTag()));
        verify(documentHashTagService).deleteAllMappingForDocument(testDocument);
        verify(documentHashTagService).mapDocumentToHashTags(eq(testDocument), eq(testHashTags));
    }

    @Test
    @DisplayName("문서 삭제 테스트")
    void deleteDocumentTest() {
        // given
        Long documentId = 1L;
        when(userContextUtil.getCurrentUser()).thenReturn(testUser);

        // when
        documentServiceApplication.deleteDocument(documentId);

        // then
        verify(userContextUtil).getCurrentUser();
        verify(documentService).deleteDocument(eq(documentId), eq(testUser));
    }

    @Test
    @DisplayName("문서 검색 테스트 - 일반 검색")
    void searchDocumentTest_NormalSearch() {
        // given
        String query = "test";
        SortMethod sortMethod = SortMethod.CREATE_AT;
        Long start = 0L;
        List<Document> documents = List.of(testDocument);

        when(documentService.searchDocument(query, start, 21L, sortMethod)).thenReturn(documents);
        when(documentLikeService.getLikes(anyLong())).thenReturn(5L);
        when(documentHashTagService.getHashTagsForDocument(any(Document.class))).thenReturn(testHashTags);

        // when
        List<DocumentsGetResponseDTO> result = documentServiceApplication.searchDocument(query, sortMethod, start);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDocument.getTitle(), result.get(0).title());
        // Note: DocumentsGetResponseDTO doesn't have a direct content field
        assertEquals(5L, result.get(0).likes());
        assertEquals(2, result.get(0).hashTags().size());

        // verify
        verify(documentService).searchDocument(query, start, 21L, sortMethod);
        verify(documentLikeService).getLikes(testDocument.getId());
        verify(documentHashTagService).getHashTagsForDocument(testDocument);
    }

    @Test
    @DisplayName("문서 검색 테스트 - 해시태그 검색")
    void searchDocumentTest_HashTagSearch() {
        // given
        String query = "#tag1";
        SortMethod sortMethod = SortMethod.CREATE_AT;
        Long start = 0L;
        List<Document> documents = List.of(testDocument);

        when(documentHashTagService.searchDocument(eq("tag1"), eq(start), eq(21L), eq(sortMethod))).thenReturn(documents);
        when(documentLikeService.getLikes(anyLong())).thenReturn(5L);
        when(documentHashTagService.getHashTagsForDocument(any(Document.class))).thenReturn(testHashTags);

        // when
        List<DocumentsGetResponseDTO> result = documentServiceApplication.searchDocument(query, sortMethod, start);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDocument.getTitle(), result.get(0).title());
        // Note: DocumentsGetResponseDTO doesn't have a direct content field
        assertEquals(5L, result.get(0).likes());
        assertEquals(2, result.get(0).hashTags().size());

        // verify
        verify(documentHashTagService).searchDocument("tag1", start, 21L, sortMethod);
        verify(documentLikeService).getLikes(testDocument.getId());
        verify(documentHashTagService).getHashTagsForDocument(testDocument);
    }
}
