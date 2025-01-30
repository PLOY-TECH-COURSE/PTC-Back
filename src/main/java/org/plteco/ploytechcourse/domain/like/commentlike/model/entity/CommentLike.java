package org.plteco.ploytechcourse.domain.like.commentlike.model.entity;

import jakarta.persistence.*;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
