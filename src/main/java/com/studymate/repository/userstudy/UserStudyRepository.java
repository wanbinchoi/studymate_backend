package com.studymate.repository.userstudy;


import com.studymate.domain.userstudy.UserStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {

    boolean existsByUser_UserNumAndStudy_StudyNum(Long userNum, Long studyNum);
    int countByStudy_StudyNum(Long studyNum);

    @Query("SELECT us FROM UserStudy us JOIN FETCH us.user WHERE us.study.studyNum = :studyNum")
    List<UserStudy> findByStudyNumWithUser(@Param("studyNum") Long studyNum);


    @Query("SELECT us FROM UserStudy us JOIN FETCH us.study WHERE us.user.userNum = :userNum")
    List<UserStudy> findByUserNumWithStudy(@Param("userNum") Long userNum);
}
