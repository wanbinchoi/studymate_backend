package com.studymate.domain.userstudy;

import com.studymate.domain.study.Study;
import com.studymate.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_study", uniqueConstraints = @UniqueConstraint(columnNames = {"user_num", "study_num"}))
public class UserStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_study_num")
    private Long userStudyNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_num", nullable = false)
    private Study study;

    @Column(name = "study_role", nullable = false)
    private String studyRole = "MEMBER";

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate(){
        joinedAt = LocalDateTime.now();
        if(studyRole == null){
            studyRole = "MEMBER";
        }
    }
}
