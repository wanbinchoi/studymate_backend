package com.studymate.repository.userrequest;

import com.studymate.domain.userrequest.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {
    boolean existsByUser_UserNumAndStudy_StudyNum(Long userNum, Long studyNum);

    @Query("SELECT ur FROM UserRequest ur JOIN FETCH ur.study WHERE ur.user.userNum =:userNum")
    List<UserRequest> findByUserNumWithStudy(@Param("userNum") Long userNum);

    @Query("SELECT ur FROM UserRequest ur JOIN FETCH ur.user WHERE ur.study.studyNum =:studyNum")
    List<UserRequest> findByStudyNumWithUser(@Param("studyNum") Long studyNum);
}
