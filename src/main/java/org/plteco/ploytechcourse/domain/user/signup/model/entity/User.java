package org.plteco.ploytechcourse.domain.user.signup.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.plteco.ploytechcourse.domain.announcement.model.entity.Announcement;
import org.plteco.ploytechcourse.domain.application.model.Student;
import org.plteco.ploytechcourse.domain.application.model.TechCourseForm;
import org.plteco.ploytechcourse.domain.document.model.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
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

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> announcements = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechCourseForm> techCourseForms = new ArrayList<>();

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