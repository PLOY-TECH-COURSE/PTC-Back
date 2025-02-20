package org.plteco.ploytechcourse.domain.document.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentUpdateRequestDTO;
import org.plteco.ploytechcourse.application.document.dto.request.DocumentWriteRequestDTO;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public static Document from(Document beforeDocs, DocumentUpdateRequestDTO updateRequestDTO) {
        return new Document(
                beforeDocs.getId(),
                beforeDocs.getUser(),
                updateRequestDTO.title(),
                updateRequestDTO.content(),
                Optional.ofNullable(updateRequestDTO.thumbnail()).orElse("기본 썸네일 이미지"),
                updateRequestDTO.introduction(),
                LocalDate.now(ZoneId.of("Asia/Seoul"))
        );
    }

    public static Document from(User user, DocumentWriteRequestDTO writeRequest) {
        return Document.builder()
                .user(user)
                .title(writeRequest.title())
                .content(writeRequest.content())
                .thumbnail(Optional.ofNullable(writeRequest.thumbnail()).orElse("기본 썸네일 이미지"))
                .introduction(writeRequest.introduction())
                .createAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
