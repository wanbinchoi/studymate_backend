package com.studymate.exception.userrequest;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;

public class RequestAlreadyProcessedException extends InvalidRequestException {
    public RequestAlreadyProcessedException(){
        super(ErrorCode.REQUEST_ALREADY_PROCESSED);
    }
    public RequestAlreadyProcessedException(Long reqNum){
        super(ErrorCode.REQUEST_ALREADY_PROCESSED,
                String.format("이미 처리된 신청 입니다. (userReNum : %d)", reqNum));
    }
}
