package com.studymate.dto.userstudy;

import com.studymate.domain.userstudy.UserStudy;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyMemberResponseDto {

    private String userId;
    private String userNm;
    private String studyRole;
    private LocalDateTime joinedAt;

    public static StudyMemberResponseDto from(UserStudy userStudy){
        return StudyMemberResponseDto.builder()
                .userId(userStudy.getUser().getUserId())
                .userNm(userStudy.getUser().getUserNm())
                .studyRole(userStudy.getStudyRole())
                .joinedAt(userStudy.getJoinedAt())
                .build();
    }
}
