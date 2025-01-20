package org.plteco.ploytechcourse.domain.comment.model.entity;

import jakarta.persistence.*;
import lombok.*;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_id")
//    private Document document;

    @Column(name = "document_id")
    private Long documentId;

    @Setter
    @Column(name = "comment", nullable = false)
    private String comment;

}
