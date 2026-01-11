package com.studymate.exception.userstudy;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.NotFoundException;

public class UserStudyNotFoundException extends NotFoundException {

    public UserStudyNotFoundException(){
        super(ErrorCode.USER_STUDY_NOT_FOUND);
    }
    public UserStudyNotFoundException(Long userStudyNum) {
        super(ErrorCode.USER_STUDY_NOT_FOUND,
                String.format("참여 기록을 찾을 수 없습니다. (userStudyNum: %d)", userStudyNum));
    }

}
