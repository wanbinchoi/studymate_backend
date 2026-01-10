package com.studymate.exception.study;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.NotFoundException;

public class StudyNotFoundException extends NotFoundException {

    public StudyNotFoundException() {
        super(ErrorCode.STUDY_NOT_FOUND);
    }

    public StudyNotFoundException(Long studyNum){
        super(ErrorCode.STUDY_NOT_FOUND,
                String.format("스터디를 찾을 수 없습니다. (studyNum: %d)", studyNum));
    }
}
