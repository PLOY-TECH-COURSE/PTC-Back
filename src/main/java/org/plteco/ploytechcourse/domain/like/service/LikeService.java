package org.plteco.ploytechcourse.domain.like.service;

public interface LikeService {
    void addLike(long targetId, long userId);
    void removeLike(long targetId, long userId);
    boolean isLiked(long targetId, long userId);
    long getLikes(long targetId);
}
