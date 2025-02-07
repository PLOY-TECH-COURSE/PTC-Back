package org.plteco.ploytechcourse.application.favorite;

import lombok.RequiredArgsConstructor;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.favorite.service.FavoriteService;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteServiceApplication {

    private final FavoriteService favoriteService;
    private final UserContextUtil userContextUtil;
//    private final DocumentRepository documentRepository;
    public void registerFavorite(long documentId) {
        User user = userContextUtil.getCurrentUser();

//        if(documentRepository.existById(documentId)){
//            throw new PltecoException("해당 문서는 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//        }

    }


}
