package com.studymate.exception.userrequest;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;

public class AlreadyRequestedException extends InvalidRequestException {
    public AlreadyRequestedException(){
        super(ErrorCode.ALREADY_REQUESTED);
    }
    public AlreadyRequestedException(Long reqNum){
        super(ErrorCode.ALREADY_REQUESTED
            ,String.format("이미 신청한 스터디입니다. (userReNum : %d)", reqNum));
    }
}
