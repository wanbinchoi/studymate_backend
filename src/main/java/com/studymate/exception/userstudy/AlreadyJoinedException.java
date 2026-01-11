package com.studymate.exception.userstudy;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;

public class AlreadyJoinedException extends InvalidRequestException {
    public AlreadyJoinedException() {
        super(ErrorCode.ALREADY_JOINED);
    }
}
