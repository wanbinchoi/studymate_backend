package com.studymate.dto.userstudy;

import com.studymate.domain.userstudy.UserStudy;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStudyResponseDto {

    private Long userStudyNum;
    private Long studyNum;
    private String studyTitle;
    private String studyRole;
    private LocalDateTime joinedAt;

    public static UserStudyResponseDto from(UserStudy userStudy){
        return UserStudyResponseDto.builder()
                .userStudyNum(userStudy.getUserStudyNum())
                .studyNum(userStudy.getStudy().getStudyNum())
                .studyTitle(userStudy.getStudy().getStudyTitle())
                .studyRole(userStudy.getStudyRole())
                .joinedAt(userStudy.getJoinedAt())
                .build();
    }
}
