package com.studymate.exception.study;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;

public class StudyCapacityExceededException extends InvalidRequestException {
    public StudyCapacityExceededException() {
        super(ErrorCode.STUDY_CAPACITY_EXCEEDED);
    }

    public StudyCapacityExceededException(Long studyNum) {
        super(ErrorCode.STUDY_CAPACITY_EXCEEDED,
                String.format("스터디 정원이 초과되었습니다. (studyNum: %d)", studyNum));
    }
}
