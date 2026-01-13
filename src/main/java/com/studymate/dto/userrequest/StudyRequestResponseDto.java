package com.studymate.dto.userrequest;

import com.studymate.domain.userrequest.UserRequest;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class StudyRequestResponseDto {
    private Long reqNum;
    private String userId;
    private String userNm;
    private String reqMessage;
    private String reqStatus;
    private LocalDateTime appliedAt;

    public static StudyRequestResponseDto from(UserRequest userRequest){
        return StudyRequestResponseDto.builder()
                .reqNum(userRequest.getReqNum())
                .userId(userRequest.getUser().getUserId())
                .userNm(userRequest.getUser().getUserNm())
                .reqMessage(userRequest.getReqMessage())
                .reqStatus(userRequest.getReqStatus())
                .appliedAt(userRequest.getAppliedAt())
                .build();
    }
}
