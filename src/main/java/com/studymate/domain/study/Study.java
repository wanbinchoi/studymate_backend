package com.studymate.domain.study;

import com.studymate.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study")
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_num")
    private Long studyNum;

    @Column(name = "study_title")
    private String studyTitle;

    @Column(name = "study_desc")
    private String studyDesc;

    @Column(name = "max_member")
    private Integer maxMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leaderId;

    @Builder.Default
    @Column(name = "study_status")
    private String studyStatus = "RECRUITING";

    @Column(name = "study_created_at")
    private LocalDateTime studyCreatedAt;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @PrePersist
    protected void onCreate() {
        studyCreatedAt = LocalDateTime.now();
        if (studyStatus == null) {
            studyStatus = "RECRUITING";  // "모집중" → "RECRUITING"
        }
    }
}
