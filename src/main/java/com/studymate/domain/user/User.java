package com.studymate.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Long userNum;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

    @Column(name = "user_nm", nullable = false)
    private String userNm;

    @Column(name = "user_birth")
    private LocalDate userBirth;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "user_addr")
    private String userAddr;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_role", nullable = false)
    private String userRole = "USER"; //기본값

    @Column(name = "user_created_at", updatable = false)
    private LocalDateTime userCreatedAt;

    @PrePersist //INSERT 하기 전에 실행
    protected void onCreate() {
        userCreatedAt = LocalDateTime.now();

        if (userRole == null) {
            userRole = "USER";
        }
    }
}
