package com.studymate.dto.study;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudyStatusDto {

    @NotBlank(message = "상태는 필수 선택 사항입니다.")
    @Pattern(
            regexp = "^(RECRUITING|IN_PROGRESS|COMPLETED)$",
            message = "한 가지를 필수로 선택해야 합니다."
    )
    private String status;
}
