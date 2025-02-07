package org.plteco.ploytechcourse.api.favorite;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteServiceImpl;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    @PostMapping("/favorite/{documentId}")
    public void registerFavorite(@PathVariable("documentId") long documentId) {


    }

//    @GetMapping("/favorite")
//    public String getFavorite() {
//        return "huhon";
//    }

    @DeleteMapping("/favorite/{documentId}")
    public void deleteFavorite(@PathVariable("documentId") long documentId) {

    }

}
