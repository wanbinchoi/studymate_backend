package com.studymate.dto.study;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferLeaderRequestDto {
    @NotBlank(message = "새 방장의 아이디는 필수입니다.")
    private String newLeaderId;
}
