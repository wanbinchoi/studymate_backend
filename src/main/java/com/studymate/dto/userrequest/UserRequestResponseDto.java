package com.studymate.dto.userrequest;

import com.studymate.domain.userrequest.UserRequest;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserRequestResponseDto {
    private Long reqNum;
    private Long studyNum;
    private String studyTitle;
    private String reqStatus;
    private String reqMessage;
    private LocalDateTime appliedAt;
    private LocalDateTime processedAt;

    public static UserRequestResponseDto from(UserRequest userRequest){
        return UserRequestResponseDto.builder()
                .reqNum(userRequest.getReqNum())
                .studyNum(userRequest.getStudy().getStudyNum())
                .studyTitle(userRequest.getStudy().getStudyTitle())
                .reqStatus(userRequest.getReqStatus())
                .reqMessage(userRequest.getReqMessage())
                .appliedAt(userRequest.getAppliedAt())
                .processedAt(userRequest.getProcessedAt())
                .build();
    }
}
