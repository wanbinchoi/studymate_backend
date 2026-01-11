package com.studymate.service.userstudy;

import com.studymate.domain.study.Study;
import com.studymate.domain.user.User;
import com.studymate.domain.userstudy.UserStudy;
import com.studymate.dto.userstudy.StudyMemberResponseDto;
import com.studymate.dto.userstudy.UserStudyRequestDto;
import com.studymate.dto.userstudy.UserStudyResponseDto;
import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;
import com.studymate.exception.study.StudyAccessDeniedException;
import com.studymate.exception.study.StudyNotFoundException;
import com.studymate.exception.user.UserNotFoundException;
import com.studymate.exception.userstudy.AlreadyJoinedException;
import com.studymate.exception.userstudy.UserStudyNotFoundException;
import com.studymate.repository.study.StudyRepository;
import com.studymate.repository.user.UserRepository;
import com.studymate.repository.userstudy.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStudyService {

    private final UserStudyRepository userStudyRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        return userRepository.findByUserId(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Transactional
    public UserStudyResponseDto joinStudy(UserStudyRequestDto dto){
        User user = getCurrentUser();

        Study study = studyRepository.findById(dto.getStudyNum())
                .orElseThrow(() -> new StudyNotFoundException(dto.getStudyNum()));

        if(userStudyRepository.existsByUser_UserNumAndStudy_StudyNum(user.getUserNum(), dto.getStudyNum())){
            throw new AlreadyJoinedException();
        }

        int currentMembers = userStudyRepository.countByStudy_StudyNum(dto.getStudyNum());
        if(currentMembers >= study.getMaxMember()){
            throw new InvalidRequestException(ErrorCode.STUDY_CAPACITY_EXCEEDED);
        }

        UserStudy userStudy = UserStudy.builder()
                .user(user)
                .study(study)
                .build();

        UserStudy savedUserStudy = userStudyRepository.save(userStudy);

        return UserStudyResponseDto.from(savedUserStudy);
    }

    @Transactional(readOnly = true)
    public List<StudyMemberResponseDto> getStudyMembers(Long studyNum){
        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        List<UserStudy> userStudies = userStudyRepository.findByStudyNumWithUser(studyNum);

        return userStudies.stream()
                .map(StudyMemberResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelStudy(Long userStudyNum){
        User user = getCurrentUser();

        UserStudy userStudy = userStudyRepository.findById(userStudyNum)
                .orElseThrow(() -> new UserStudyNotFoundException(userStudyNum));

        if(!userStudy.getUser().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(
                    userStudy.getStudy().getStudyNum(),
                    user.getUserId());
        }
        userStudyRepository.deleteById(userStudyNum);
    }

    @Transactional(readOnly = true)
    public List<UserStudyResponseDto> getMyStudies(){
        User user = getCurrentUser();

        List<UserStudy> userStudies = userStudyRepository.findByUserNumWithStudy(user.getUserNum());

        return userStudies.stream()
                .map(UserStudyResponseDto::from)
                .collect(Collectors.toList());
    }

}
