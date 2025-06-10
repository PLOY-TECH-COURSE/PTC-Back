package org.plteco.ploytechcourse.announcement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementInfoDTO;
import org.plteco.ploytechcourse.application.announcement.dto.AnnouncementUserInfoDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementDetailGetResponseDTO;
import org.plteco.ploytechcourse.application.announcement.dto.response.AnnouncementGetResponseDTO;
import org.plteco.ploytechcourse.application.announcement.service.AnnouncementServiceApplication;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.announcement.service.AnnouncementService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceApplicationTest {
    @Mock
    private UserContextUtil userContextUtil;
    
    @Mock
    private ModelMapper modelMapper;
    
    @Mock
    private AnnouncementService announcementService;
    
    @InjectMocks
    private AnnouncementServiceApplication announcementServiceApplication;
    
    private User testUser;
    private Announcement testAnnouncement;
    private AnnouncementWriteRequestDTO testWriteRequestDTO;
    private AnnouncementUpdateRequestDTO testUpdateRequestDTO;
    private AnnouncementInfoDTO testAnnouncementInfoDTO;
    private AnnouncementUserInfoDTO testUserInfoDTO;
    
    @BeforeEach
    void setUp() {
        // 테스트 사용자 설정
        testUser = User.builder()
                .id(1L)
                .uid("test-user-id")
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .role(RoleEnum.ROLE_ADMIN)
                .grade(1L)
                .classNumber(1L)
                .number(1L)
                .profile("https://example.com/profile.jpg")
                .build();
                
        // 테스트 공지사항 설정
        testAnnouncement = Announcement.builder()
                .id(1L)
                .user(testUser)
                .title("Test Announcement")
                .content("Test Content")
                .thumbnail("https://example.com/thumbnail.jpg")
                .introduction("Test Introduction")
                .createAt(LocalDate.now())
                .build();
                
        // 테스트 공지사항 작성 요청 DTO 설정
        testWriteRequestDTO = new AnnouncementWriteRequestDTO(
                "Test Announcement",
                "Test Content",
                "https://example.com/thumbnail.jpg",
                "Test Introduction"
        );
        
        // 테스트 공지사항 업데이트 요청 DTO 설정
        testUpdateRequestDTO = new AnnouncementUpdateRequestDTO(
                1L,
                "Updated Announcement",
                "Updated Content",
                "https://example.com/updated-thumbnail.jpg",
                "Updated Introduction"
        );
        
        // 테스트 공지사항 정보 DTO 설정
        testAnnouncementInfoDTO = new AnnouncementInfoDTO(
                1L,
                "Test Announcement",
                "Test Content",
                LocalDate.now(),
                "https://example.com/thumbnail.jpg",
                "Test Introduction"
        );
        
        // 테스트 사용자 정보 DTO 설정
        testUserInfoDTO = new AnnouncementUserInfoDTO(
                1L,
                "test-user-id",
                "Test User",
                "https://example.com/profile.jpg"
        );
    }
    
    @Test
    @DisplayName("공지사항 목록 조회 테스트")
    void getAnnouncementsTest() {
        // given
        Long start = 0L;
        List<Announcement> announcements = List.of(testAnnouncement);
        
        when(announcementService.getAnnouncements(start, 21L)).thenReturn(announcements);
        
        // when
        List<AnnouncementGetResponseDTO> result = announcementServiceApplication.getAnnouncements(start);
        
        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAnnouncement.getId(), result.get(0).announcementId());
        assertEquals(testAnnouncement.getTitle(), result.get(0).title());
        assertEquals(testAnnouncement.getIntroduction(), result.get(0).introduction());
        assertEquals(testAnnouncement.getThumbnail(), result.get(0).thumbnail());
        assertEquals(testAnnouncement.getUser().getId(), result.get(0).userId());
        assertEquals(testAnnouncement.getUser().getName(), result.get(0).name());
        assertEquals(testAnnouncement.getCreateAt(), result.get(0).date());
        
        // verify
        verify(announcementService).getAnnouncements(start, 21L);
    }
    
    @Test
    @DisplayName("공지사항 상세 조회 테스트")
    void getAnnouncementDetailTest() {
        // given
        Long announcementId = 1L;
        
        when(announcementService.getAnnouncement(announcementId)).thenReturn(testAnnouncement);
        when(modelMapper.map(testAnnouncement, AnnouncementInfoDTO.class)).thenReturn(testAnnouncementInfoDTO);
        when(announcementService.getAnnouncementUser(announcementId)).thenReturn(testUser);
        when(modelMapper.map(testUser, AnnouncementUserInfoDTO.class)).thenReturn(testUserInfoDTO);
        when(announcementService.getUserGeneration(testAnnouncement)).thenReturn(Optional.of(1L));
        
        // when
        AnnouncementDetailGetResponseDTO result = announcementServiceApplication.getAnnouncementDetail(announcementId);
        
        // then
        assertNotNull(result);
        assertEquals(testAnnouncementInfoDTO, result.documentInfoDTO());
        assertEquals(testUserInfoDTO, result.userInfoDTO());
        assertEquals("1", result.generation());
        
        // verify
        verify(announcementService).getAnnouncement(announcementId);
        verify(modelMapper).map(testAnnouncement, AnnouncementInfoDTO.class);
        verify(announcementService).getAnnouncementUser(announcementId);
        verify(modelMapper).map(testUser, AnnouncementUserInfoDTO.class);
        verify(announcementService).getUserGeneration(testAnnouncement);
    }
    
    @Test
    @DisplayName("공지사항 작성 테스트")
    void writeAnnouncementTest() {
        // given
        when(userContextUtil.getCurrentUser()).thenReturn(testUser);
        when(announcementService.writeDocument(eq(testUser), any(AnnouncementWriteRequestDTO.class))).thenReturn(testAnnouncement);
        
        // when
        announcementServiceApplication.writeAnnouncement(testWriteRequestDTO);
        
        // then
        verify(userContextUtil).getCurrentUser();
        verify(announcementService).writeDocument(eq(testUser), eq(testWriteRequestDTO));
    }
    
    @Test
    @DisplayName("공지사항 업데이트 테스트")
    void updateAnnouncementTest() {
        // given
        when(announcementService.updateAnnouncement(any(AnnouncementUpdateRequestDTO.class))).thenReturn(testAnnouncement);
        
        // when
        announcementServiceApplication.updateAnnouncement(testUpdateRequestDTO);
        
        // then
        verify(announcementService).updateAnnouncement(eq(testUpdateRequestDTO));
    }
    
    @Test
    @DisplayName("공지사항 삭제 테스트")
    void deleteAnnouncementTest() {
        // given
        Long announcementId = 1L;
        
        // when
        announcementServiceApplication.deleteAnnouncement(announcementId);
        
        // then
        verify(announcementService).deleteAnnouncement(eq(announcementId));
    }
    
    @Test
    @DisplayName("멘토 공지사항 상세 조회 테스트")
    void getAnnouncementDetailTest_Mentor() {
        // given
        Long announcementId = 1L;
        
        when(announcementService.getAnnouncement(announcementId)).thenReturn(testAnnouncement);
        when(modelMapper.map(testAnnouncement, AnnouncementInfoDTO.class)).thenReturn(testAnnouncementInfoDTO);
        when(announcementService.getAnnouncementUser(announcementId)).thenReturn(testUser);
        when(modelMapper.map(testUser, AnnouncementUserInfoDTO.class)).thenReturn(testUserInfoDTO);
        when(announcementService.getUserGeneration(testAnnouncement)).thenReturn(Optional.empty());
        
        // when
        AnnouncementDetailGetResponseDTO result = announcementServiceApplication.getAnnouncementDetail(announcementId);
        
        // then
        assertNotNull(result);
        assertEquals(testAnnouncementInfoDTO, result.documentInfoDTO());
        assertEquals(testUserInfoDTO, result.userInfoDTO());
        assertEquals("멘토", result.generation());
        
        // verify
        verify(announcementService).getAnnouncement(announcementId);
        verify(modelMapper).map(testAnnouncement, AnnouncementInfoDTO.class);
        verify(announcementService).getAnnouncementUser(announcementId);
        verify(modelMapper).map(testUser, AnnouncementUserInfoDTO.class);
        verify(announcementService).getUserGeneration(testAnnouncement);
    }
}