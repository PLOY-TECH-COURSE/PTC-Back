package org.plteco.ploytechcourse.domain.jwt.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * RefreshToken 엔티티 클래스는 JWT 리프레시 토큰을 데이터베이스에 저장하기 위한 엔티티입니다.
 * <p>
 * 이 클래스는 리프레시 토큰의 정보와 관련된 데이터를 저장하며, 리프레시 토큰의 UID, 이메일, 토큰 문자열,
 * 토큰의 만료 시간, 생성 시간을 포함합니다.
 * </p>
 */
@Entity
@Table(name = "refresh_token") // 데이터베이스에서 'refresh_token' 테이블에 매핑
@Getter // getter 메서드를 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
@Builder // 빌더 패턴을 사용하여 객체 생성
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략을 Identity로 설정 (자동 증가)
    private Long id;

    @Column(nullable = false, length = 30) // 'uid'는 null이 아니며, 최대 길이는 30
    private String uid;

    @Column(nullable = false, length = 320) // 'email'은 null이 아니며, 최대 길이는 320
    private String email;

    @Column(nullable = false) // 'token'은 null이 아니어야 함
    private String token;

    @Column(name = "expires_at", nullable = false) // 'expiresAt'은 null이 아니며, 만료 시간
    @Temporal(TemporalType.TIMESTAMP) // 'expiresAt' 필드는 날짜와 시간까지 저장
    private Date expiresAt;

    @Column(name = "created_at", nullable = false) // 'createdAt'은 null이 아니며, 생성 시간
    @Temporal(TemporalType.TIMESTAMP) // 'createdAt' 필드는 날짜와 시간까지 저장
    private Date createdAt;

}
