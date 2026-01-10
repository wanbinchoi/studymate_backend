package com.studymate.repository.study;


import com.studymate.domain.study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    // 1. 제목으로 검색(부분 일치 LIKE)
    List<Study> findByStudyTitleContaining(String keyword);

    // 2. 상태로 필터링 검색
    List<Study> findByStudyStatus(String status);

    // 3. 리더가 만든 스터디 조회
    List<Study> findByLeaderId_UserNum(Long userNum);

    // 4. 페이징 적요한 전체 조회
    Page<Study> findAll(Pageable pageable);

    // 5. 제목 검색 + 페이징
    Page<Study> findByStudyTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT s FROM Study s JOIN FETCH s.leaderId WHERE s.studyNum = :studyNum")
    Optional<Study> findByIdWithLeader(@Param("studyNum") Long studyNum);
}
