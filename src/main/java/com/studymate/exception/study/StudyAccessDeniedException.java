package com.studymate.exception.study;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.UnauthorizedException;

public class StudyAccessDeniedException extends UnauthorizedException {

    public StudyAccessDeniedException() {
        super(ErrorCode.STUDY_ACCESS_DENIED);
    }

    public StudyAccessDeniedException(Long studyNum, String userId){
        super(ErrorCode.STUDY_ACCESS_DENIED,
                String.format("스터디를 수정/삭제할 권한이 없습니다. (studyNum: %d, userId: %s)", studyNum, userId));
    }
}
