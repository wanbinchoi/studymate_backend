package com.studymate.service.usercategory;

import com.studymate.domain.category.Category;
import com.studymate.domain.user.User;
import com.studymate.domain.usercategory.UserCategory;
import com.studymate.dto.usercategory.UserCategoryRequestDto;
import com.studymate.dto.usercategory.UserCategoryResponseDto;
import com.studymate.repository.category.CategoryRepository;
import com.studymate.repository.user.UserRepository;
import com.studymate.repository.usercategory.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final CategoryRepository categoryRepository;

    //현재 로그인한 사용자 갖고오기
    private User getCurrentUser(){

        // 1. SecurityContext에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. username 추출
        String username = authentication.getName();

        // 3. username으로 User 조회
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public void addUserCategory(UserCategoryRequestDto dto){

        // 1. 현재 사용자 조회
        User user = getCurrentUser();

        // 2. 카테고리 조회(없으면 예외)
        Category category = categoryRepository.findById(dto.getCgNum())
                .orElseThrow( () -> new RuntimeException("카테고리를 찾을 수 없습니다."));


        if(userCategoryRepository.existsByUser_UserNumAndCategory_CgNum(user.getUserNum(), dto.getCgNum())){
            throw new RuntimeException("이미 등록된 카테고리 입니다.");
        }

        UserCategory userCategory = UserCategory.builder()
                .user(user)
                .category(category)
                .build();

        userCategoryRepository.save(userCategory);
    }

    public void deleteUserCategory(Long cgNum){

        // 1. 현재 사용자 조회
        User user = getCurrentUser();

        // 2. 카테고리 조회
        UserCategory userCategory = userCategoryRepository.findByUser_UserNumAndCategory_CgNum(user.getUserNum(), cgNum)
                .orElseThrow(() -> new RuntimeException("등록되지 않은 카테고리입니다."));

        // 3. 삭제
        userCategoryRepository.delete(userCategory);
    }

    public List<UserCategoryResponseDto> getUserCategories(){

        // 1. 사용자 조회
        User user = getCurrentUser();

        // 2. 사용자 카테고리목록 조회
        List<UserCategory> userCategories = userCategoryRepository.findByUser_UserNum(user.getUserNum());

        // 3. DTO 변환
        List<UserCategoryResponseDto> dtoList = userCategories.stream()
                .map(UserCategoryResponseDto::from)
                .collect(Collectors.toList());

        return dtoList;
    }
}
