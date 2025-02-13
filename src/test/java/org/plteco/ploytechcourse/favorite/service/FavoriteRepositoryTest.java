//package org.plteco.ploytechcourse.favorite.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
//import org.plteco.ploytechcourse.domain.favorite.model.entity.FavoriteId;
//import org.plteco.ploytechcourse.domain.favorite.repository.FavoriteRepository;
//import org.plteco.ploytechcourse.domain.favorite.service.FavoriteServiceImpl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class FavoriteServiceTest {
//
//    @Mock
//    private FavoriteRepository favoriteRepository;
//
//    @InjectMocks
//    private FavoriteServiceImpl favoriteService;
//
//    @Test
//    @DisplayName("즐겨찾기 등록 테스트")
//    public void testRegisterFavorite() {
//        // Given
//        FavoriteId favoriteId = FavoriteId.builder()
//                .user_id(2)
//                .document_id(2)
//                .build();
//
//        Favorite favorite = Favorite.builder()
//                .id(favoriteId)
//                .build();
//
//        when(favoriteRepository.save(favorite)).thenReturn(favorite);
//
//        // When
//        Favorite savedFavorite = favoriteService.registerFavorite(favorite.getId().getUser_id(), favorite.getId().getDocument_id());
//
//        // Then
//        assertNotNull(savedFavorite);
//        assertEquals(favoriteId, savedFavorite.getId());
//        verify(favoriteRepository, times(1)).save(favorite); // save 메서드 호출 여부 검증
//    }
//
//    @Test
//    @DisplayName("즐겨찾기 삭제 테스트")
//    public void testDeleteFavorite() {
//        // Given
//        FavoriteId favoriteId = FavoriteId.builder()
//                .user_id(2)
//                .document_id(2)
//                .build();
//
//        doNothing().when(favoriteRepository).deleteById(favoriteId);
//
//        // 삭제 테스트
//        favoriteService.deleteFavorite(favoriteId.getUser_id(), favoriteId.getDocument_id());
//
//        // Then
//        verify(favoriteRepository, times(1)).deleteById(favoriteId); // deleteById 호출 여부 검증
//    }
//}
