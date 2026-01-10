package com.studymate.dto.study;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyCreateRequestDto {

    @NotBlank(message = "스터디 제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "제목은 2자 이상 100자 이하입니다.")
    private String studyTitle;

    @NotBlank(message = "스터디 설명은 필수입니다.")
    @Size(max = 500, message = "설명은 500자 이하입니다.")
    private String studyDesc;

    @NotNull(message = "최대 인원은 필수입니다.")
    @Min(value = 2, message = "최소 2명 이상이어야 합니다.")
    @Max(value = 100, message = "최대 100명까지 가능합니다.")
    private Integer maxMember;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotEmpty(message = "카테고리는 최소 1개 이상 선택해야합니다.")
    private List<Long> categoryIds;
}
