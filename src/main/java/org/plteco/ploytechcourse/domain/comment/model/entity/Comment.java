package org.plteco.ploytechcourse.domain.comment.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.shared.jwt.UserContextUtil;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_id")
//    private Document document;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "comment", nullable = false)
    private String comment;

}
