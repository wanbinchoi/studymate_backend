package com.studymate.dto.usercategory;

import com.studymate.domain.usercategory.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCategoryResponseDto {
    private Long userCgNum;
    private Long cgNum;
    private String cgNm;

    public static UserCategoryResponseDto from(UserCategory userCategory){
        return UserCategoryResponseDto.builder()
                .userCgNum(userCategory.getUserCgNum())
                .cgNum(userCategory.getUserCgNum())
                .cgNm(userCategory.getCategory().getCgNm())
                .build();
    }
}
