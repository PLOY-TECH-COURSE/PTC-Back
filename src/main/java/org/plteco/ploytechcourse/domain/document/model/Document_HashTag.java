package org.plteco.ploytechcourse.domain.document.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor     // builder를 위한 생성자
@NoArgsConstructor      // bean 등록을 위해서는 매개변수가 없는 생성자 필요
@Builder
@Table(name = "document_hashtag")
public class Document_HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 실제로 데이터를 필요로 할 때만 데이터베이스에서 가져온다.
    @JoinColumn(name="document_id", nullable=false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 실제로 데이터를 필요로 할 때만 데이터베이스에서 가져온다.
    @JoinColumn(name="hashtag_id", nullable=false)
    private HashTag hashtag;
}
