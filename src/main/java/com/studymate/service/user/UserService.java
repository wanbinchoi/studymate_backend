package com.studymate.service.user;

import com.studymate.dto.auth.LoginRequest;
import com.studymate.dto.auth.SignupRequest;
import com.studymate.dto.auth.SignupResponse;

public interface UserService {

    SignupResponse signup(SignupRequest request);

    SignupResponse login(LoginRequest request);
}
