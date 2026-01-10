package com.studymate.dto.study;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyUpdateRequestDto {

    @Size(min = 20, max = 100, message = "제목은 2자 이상 100자 이하입니다.")
    private String studyTitle;

    @Size(max = 500, message = "설명은 500자 이하입니다.")
    private String studyDesc;

    @Min(value = 2, message = "최소 2명 이상이어야 합니다.")
    @Max(value = 100, message = "최대 100명까지 가능합니다.")
    private Integer maxMember;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> categoryIds;

    @Pattern(regexp = "RECRUITING|IN_PROGRESS|COMPLETED|CANCELLED", message = "유효하지 않은 스터디 상태입니다.")
    private String studyStatus;
}
