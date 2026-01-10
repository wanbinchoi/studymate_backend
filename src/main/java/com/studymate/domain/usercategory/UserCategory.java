package com.studymate.domain.usercategory;

import com.studymate.domain.category.Category;
import com.studymate.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_category", uniqueConstraints = @UniqueConstraint(columnNames = {"user_num", "cg_num"}))
//같은 카테고리 중복 선택 방지
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cg_num")
    private Long userCgNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cg_num", nullable = false)
    private Category category;
}
