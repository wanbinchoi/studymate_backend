package com.studymate.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cg_num")
    private Long cgNum;

    @Column(name = "cg_nm", unique = true, nullable = false)
    private String cgNm;

}
