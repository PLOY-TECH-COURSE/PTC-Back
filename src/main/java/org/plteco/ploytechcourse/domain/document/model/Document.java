package org.plteco.ploytechcourse.domain.document.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.comment.model.entity.Comment;
import org.plteco.ploytechcourse.domain.favorite.model.entity.Favorite;
import org.plteco.ploytechcourse.domain.like.documentlike.model.DocumentLike;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor      // 매개변수 없는 생성자를 만들어서 JPA가 객체를 생성할 수 있도록 함
@AllArgsConstructor
@Builder
@Getter
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 실제로 데이터를 필요로 할 때만 데이터베이스에서 가져온다.
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "created_at", nullable = false)
    private LocalDate createAt;

    @Builder.Default
    @Column(name = "document_like_count", nullable = false)
    private Long documentLikeCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentLike> likes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentComment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorite = new ArrayList<>();

    public void increaseLike() {
        this.documentLikeCount++;
    }

    public void decreaseLike() {
        if (this.documentLikeCount > 0)
            this.documentLikeCount--;
    }

    @PrePersist
    public void prePersist() {
        if (this.documentLikeCount == null) {
            this.documentLikeCount = 0L;
        }
    }

    public static Document from(Document beforeDocs, DocumentUpdateRequestDTO updateRequestDTO) {
        return new Document(
                beforeDocs.getId(),
                beforeDocs.getUser(),
                updateRequestDTO.title(),
                updateRequestDTO.content(), //
                Optional.ofNullable(updateRequestDTO.thumbnail()).orElse("https://storage.googleapis.com/ploytechcourse-version3/391b0b82-c522-4fd5-9a75-5a1488c21b7e"),
                updateRequestDTO.introduction(),
                LocalDate.now(ZoneId.of("Asia/Seoul")),
                beforeDocs.getDocumentLikeCount(),
                beforeDocs.getLikes(),
                beforeDocs.getComments(),
                beforeDocs.getFavorite()
        );
    }
    public static Document from(User user, DocumentWriteRequestDTO writeRequest) {
        return Document.builder()
                .user(user)
                .title(writeRequest.title())
                .content(writeRequest.content())
                .thumbnail(Optional.ofNullable(writeRequest.thumbnail()).orElse("https://storage.googleapis.com/ploytechcourse/4.png"))
                .introduction(writeRequest.introduction())
                .createAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
