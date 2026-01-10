package com.studymate.repository.studycategory;

import com.studymate.domain.studycategory.StudyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyCategoryRepository extends JpaRepository<StudyCategory, Long> {

    // 1. 특정 스터디의 모든 카테고리 조회
    List<StudyCategory> findByStudy_StudyNum(Long studyNum);

    // 2. 특정 카테고리의 모든 스터디 조회
    List<StudyCategory> findByCategory_CgNum(Long cgNum);

    // 3. 스터디 삭제 시 관련 StudyCategory도 삭제
    void deleteByStudy_StudyNum(Long studyNum);

    // 여러 스터디의 카테고리를 Category와 함께 한 번에 조회
    @Query("SELECT sc FROM StudyCategory sc " +
            "JOIN FETCH sc.category " +
            "WHERE sc.study.studyNum IN :studyNums")
    List<StudyCategory> findByStudyNumsWithCategory(@Param("studyNums") List<Long> studyNums);
}
