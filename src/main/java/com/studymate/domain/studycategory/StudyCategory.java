package com.studymate.domain.studycategory;

import com.studymate.domain.category.Category;
import com.studymate.domain.study.Study;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study_category", uniqueConstraints = @UniqueConstraint(columnNames = {"study_num", "cg_num"}))
public class StudyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_cg_num")
    private Long studyCgNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_num", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cg_num", nullable = false)
    private Category category;
}
