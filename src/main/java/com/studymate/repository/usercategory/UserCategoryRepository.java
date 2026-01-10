package com.studymate.repository.usercategory;

import com.studymate.domain.usercategory.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findByUser_UserNum(Long userNum);

    Optional<UserCategory> findByUser_UserNumAndCategory_CgNum(Long userNum, Long cgNum);

    boolean existsByUser_UserNumAndCategory_CgNum(Long userNum, Long cgNum);
}
