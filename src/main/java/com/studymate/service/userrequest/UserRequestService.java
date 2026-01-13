package com.studymate.service.userrequest;

import com.studymate.domain.study.Study;
import com.studymate.domain.user.User;
import com.studymate.domain.userrequest.UserRequest;
import com.studymate.domain.userstudy.UserStudy;
import com.studymate.dto.userrequest.StudyRequestResponseDto;
import com.studymate.dto.userrequest.UserRequestCreateDto;
import com.studymate.dto.userrequest.UserRequestResponseDto;
import com.studymate.exception.study.StudyAccessDeniedException;
import com.studymate.exception.study.StudyCapacityExceededException;
import com.studymate.exception.study.StudyNotFoundException;
import com.studymate.exception.user.UserNotFoundException;
import com.studymate.exception.userrequest.AlreadyRequestedException;
import com.studymate.exception.userrequest.RequestAlreadyProcessedException;
import com.studymate.exception.userrequest.UserRequestNotFoundException;
import com.studymate.exception.userstudy.AlreadyJoinedException;
import com.studymate.repository.study.StudyRepository;
import com.studymate.repository.user.UserRepository;
import com.studymate.repository.userrequest.UserRequestRepository;
import com.studymate.repository.userstudy.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRequestService {

    private final UserRepository userRepository;
    private final UserRequestRepository userRequestRepository;
    private final StudyRepository studyRepository;
    private final UserStudyRepository userStudyRepository;

    private User getCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        return userRepository.findByUserId(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Transactional
    public UserRequestResponseDto createRequest(UserRequestCreateDto dto){
        User user = getCurrent();

        if(userRequestRepository.existsByUser_UserNumAndStudy_StudyNum(getCurrent().getUserNum(), dto.getStudyNum())){
            throw new AlreadyRequestedException();
        }

        if(userStudyRepository.existsByUser_UserNumAndStudy_StudyNum(getCurrent().getUserNum(), dto.getStudyNum())){
            throw new AlreadyJoinedException();
        }
        Study study = studyRepository.findById(dto.getStudyNum())
                .orElseThrow(() -> new StudyNotFoundException(dto.getStudyNum()));

        UserRequest userRequest = UserRequest.builder()
                .user(user)
                .study(study)
                .reqMessage(dto.getReqMessage())
                .reqStatus("PENDING")
                .appliedAt(LocalDateTime.now())
                .build();

        UserRequest savedRequest = userRequestRepository.save(userRequest);

        return UserRequestResponseDto.from(userRequest);
    }

    @Transactional(readOnly = true)
    public List<UserRequestResponseDto> getMyRequests(){

        User user = getCurrent();

        List<UserRequest> userRequests = userRequestRepository.findByUserNumWithStudy(user.getUserNum());

        return userRequests.stream()
                .map(UserRequestResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudyRequestResponseDto> getStudyRequests(Long studyNum){

        User user = getCurrent();

        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(studyNum, user.getUserId());
        }

        List<UserRequest> studyRequests = userRequestRepository.findByStudyNumWithUser(studyNum);

        return studyRequests.stream()
                .map(StudyRequestResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudyRequestResponseDto approveRequest(Long reqNum){
        User user = getCurrent();

        UserRequest userRequest = userRequestRepository.findById(reqNum)
                .orElseThrow(() -> new UserRequestNotFoundException(reqNum));
        Study study = userRequest.getStudy();

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(study.getStudyNum(), user.getUserId());
        }

        if(!"PENDING".equals(userRequest.getReqStatus())){
            throw new RequestAlreadyProcessedException(reqNum);
        }

        int currentMember = userStudyRepository.countByStudy_StudyNum(study.getStudyNum());
        if(currentMember >= study.getMaxMember()){
            throw new StudyCapacityExceededException(study.getStudyNum());
        }
        userRequest.setReqStatus("APPROVED");
        userRequest.setProcessedAt(LocalDateTime.now());

        UserStudy userStudy = UserStudy.builder()
                .user(userRequest.getUser())
                .study(study)
                .studyRole("MEMBER")
                .build();
        userStudyRepository.save(userStudy);

        return StudyRequestResponseDto.from(userRequest);
    }

    @Transactional
    public StudyRequestResponseDto rejectRequest(Long reqNum){
        User user = getCurrent();

        UserRequest userRequest = userRequestRepository.findById(reqNum)
                .orElseThrow(() -> new UserRequestNotFoundException(reqNum));
        Study study = userRequest.getStudy();

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(study.getStudyNum(), user.getUserId());
        }

        if(!"PENDING".equals(userRequest.getReqStatus())){
            throw new RequestAlreadyProcessedException(reqNum);
        }

        userRequest.setReqStatus("REJECTED");
        userRequest.setProcessedAt(LocalDateTime.now());

        return StudyRequestResponseDto.from(userRequest);
    }

}
