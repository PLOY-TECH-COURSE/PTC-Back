package org.plteco.ploytechcourse.domain.announcement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementUpdateRequestDTO;
import org.plteco.ploytechcourse.application.announcement.dto.request.AnnouncementWriteRequestDTO;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Entity
@NoArgsConstructor      // 매개변수 없는 생성자를 만들어서 JPA가 객체를 생성할 수 있도록 함
@AllArgsConstructor
@Builder
@Getter
@Table(name = "announcement")
public class Announcement {
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

    public static Announcement from(Announcement beforeDocs, AnnouncementUpdateRequestDTO updateRequestDTO) {
        return new Announcement(
                beforeDocs.getId(),
                beforeDocs.getUser(),
                updateRequestDTO.title(),
                updateRequestDTO.content(),
                Optional.ofNullable(updateRequestDTO.thumbnail()).orElse("기본 썸네일 이미지"),
                updateRequestDTO.introduction(),
                LocalDate.now(ZoneId.of("Asia/Seoul"))
        );
    }

    public static Announcement from(User user, AnnouncementWriteRequestDTO writeRequest) {
        return Announcement.builder()
                .user(user)
                .title(writeRequest.title())
                .content(writeRequest.content())
                .thumbnail(Optional.ofNullable(writeRequest.thumbnail()).orElse("기본 썸네일 이미지"))
                .introduction(writeRequest.introduction())
                .createAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
