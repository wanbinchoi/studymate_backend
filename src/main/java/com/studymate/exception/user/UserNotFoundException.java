package com.studymate.exception.user;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String userId){
        super(ErrorCode.USER_NOT_FOUND,
                String.format("사용자를 찾을 수 없습니다. (userId: %s)", userId));
    }

    public UserNotFoundException(Long userNum){
        super(ErrorCode.USER_NOT_FOUND,
                String.format("사용자를 찾을 수 없습니다. (userNum: %d)", userNum));
    }
}
