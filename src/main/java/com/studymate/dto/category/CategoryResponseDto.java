package com.studymate.dto.category;

import com.studymate.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private Long cgNum;

    private String cgNm;

    public static CategoryResponseDto from(Category category){
        return CategoryResponseDto.builder()
                .cgNum(category.getCgNum())
                .cgNm(category.getCgNm())
                .build();
    }
}
