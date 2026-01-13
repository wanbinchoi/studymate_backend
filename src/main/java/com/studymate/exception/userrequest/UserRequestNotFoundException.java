package com.studymate.exception.userrequest;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.NotFoundException;

public class UserRequestNotFoundException extends NotFoundException {
    public UserRequestNotFoundException(){
        super(ErrorCode.USER_REQUEST_NOT_FOUND);
    }
    public UserRequestNotFoundException(Long reqNum){
        super(ErrorCode.USER_REQUEST_NOT_FOUND,
                String.format("신청 기록을 찾을 수 없습니다. (userReNum: %d)", reqNum));
    }
}
