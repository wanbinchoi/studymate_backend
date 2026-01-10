package com.studymate.domain.userrequest;

import com.studymate.domain.study.Study;
import com.studymate.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_request", uniqueConstraints = @UniqueConstraint(columnNames = {"user_num", "study_num"}))
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_num")
    private Long reqNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_num", nullable = false)
    private Study study;

    @Column(name = "req_status", nullable = false)
    private String reqStatus = "PENDING";

    @Column(name = "req_message", columnDefinition = "TEXT")
    private String reqMessage;

    @Column(name = "applied_at", updatable = false)
    private LocalDateTime appliedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @PrePersist
    protected void onCreate(){
        appliedAt = LocalDateTime.now();
        if (reqStatus == null){
            reqStatus = "PENDING";
        }
    }
}
