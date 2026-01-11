package com.studymate.dto.userstudy;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserStudyRequestDto {

    @NotNull(message = "스터디 번호는 필수 입니다.")
    private Long studyNum;
}
