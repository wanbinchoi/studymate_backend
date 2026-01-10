package com.studymate.dto.auth;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {

    private Long userNum;
    private String userId;
    private String userEmail;
    private String accessToken;
    private String message;

}
