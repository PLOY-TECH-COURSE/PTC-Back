package org.plteco.ploytechcourse.domain.user.signup.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    private String bio;

    @Column(nullable = false)
    private Long grade;

    @Column(nullable = false)
    private Long classNumber;

    @Column(nullable = false)
    private Long number;

    @Builder
    public User(String uid, String name, String email, String password, String profile, RoleEnum role, String bio, Long grade, Long classNumber, Long number) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.role = role;
        this.bio = bio;
        this.grade = grade;
        this.classNumber = classNumber;
        this.number = number;
    }
    public void updateRole(RoleEnum role) {
        this.role = role;
    }
    public void updateProfile(String profile) {this.profile = profile;}
    public void updateBio(String bio) {this.bio = bio;}
    public void updatePassword(String password) {this.password = password;}
}