package com.studymate.dto.study;

import com.studymate.domain.study.Study;
import com.studymate.domain.studycategory.StudyCategory;
import com.studymate.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyListResponseDto {

    private Long studyNum;
    private String studyTitle;
    private String studyDesc;
    private String studyStatus;
    private Integer maxMember;
    private Integer currentMember;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaderId;
    private List<String> cgNms;

    public static StudyListResponseDto from(Study study, List<StudyCategory> studyCategories, Integer currentMember){
        return StudyListResponseDto.builder()
                .studyNum(study.getStudyNum())
                .studyTitle(study.getStudyTitle())
                .studyDesc(study.getStudyDesc())
                .maxMember(study.getMaxMember())
                .currentMember(currentMember)
                .studyStatus(study.getStudyStatus())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .leaderId(study.getLeaderId().getUserId())
                .cgNms(studyCategories.stream()
                        .map(sc -> sc.getCategory().getCgNm())
                        .collect(Collectors.toList()))
                .build();
    }

}
