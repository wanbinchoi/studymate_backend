package com.studymate.service.user;

import com.studymate.config.JwtTokenProvider;
import com.studymate.domain.user.User;
import com.studymate.dto.auth.LoginRequest;
import com.studymate.dto.auth.SignupRequest;
import com.studymate.dto.auth.SignupResponse;
import com.studymate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request){

        //1. 아이디 중복 체크
        if(userRepository.existsByUserId(request.getUserId())){
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        //2. 이메일 중복 체크
        if(userRepository.existsByUserEmail(request.getUserEmail())){
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        //3. User 엔티티 생성
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserPwd(passwordEncoder.encode(request.getUserPwd())); //비밀번호 암호화
        user.setUserEmail(request.getUserEmail());
        user.setUserNm(request.getUserNm());
        user.setUserRole("USER");

        // 4.DB에 저장
        User savedUser = userRepository.save(user);

        //5. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(savedUser.getUserId());

        //6. 응답 DTO 생성
        return SignupResponse.builder()
                .userNum(savedUser.getUserNum())
                .userId(savedUser.getUserId())
                .userEmail(savedUser.getUserEmail())
                .accessToken(accessToken)
                .message("회원가입이 완료되었습니다.")
                .build();
    }

    @Override
    @Transactional
    public SignupResponse login(LoginRequest request){

        //1. userId로 사용자 조회
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        //2. 비밀번호 확인
        if(!passwordEncoder.matches(request.getUserPwd(), user.getUserPwd())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        //3. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(user.getUserId());

        // 4. 응답 DTO 생성
        return SignupResponse.builder()
                .userNum(user.getUserNum())
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .accessToken(accessToken)
                .message("로그인 성공")
                .build();
    }
}
