package com.studymate.dto.userrequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestCreateDto {

    @NotNull(message = "스터디 번호는 필수입니다.")
    private Long studyNum;

    @NotBlank(message = "신청 메시지는 필수입니다.")
    @Size(min = 10, max = 500, message = "신청 메시지는 최소 10자 이상 500wk 이하입니다.")
    private String reqMessage;

}
