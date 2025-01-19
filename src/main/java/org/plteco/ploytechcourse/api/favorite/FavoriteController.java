package org.plteco.ploytechcourse.api.favorite;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserContextUtil userContextUtil;

    @PostMapping("/favorite/{documentId}")
    public void registerFavorite(@PathVariable("documentId") long documentId) {
        /*
        * jwt에서 user_id를 가져옵니다.
         */
        long user_id = userContextUtil.getId();
        favoriteService.registerFavorite(user_id, documentId);
    }

//    @GetMapping("/favorite")
//    public String getFavorite() {
//        return "huhon";
//    }

    @DeleteMapping("/favorite/{documentId}")
    public void deleteFavorite(@PathVariable("documentId") long documentId) {

        long userId = userContextUtil.getId();
        favoriteService.deleteFavorite(userId, documentId);
    }

}
