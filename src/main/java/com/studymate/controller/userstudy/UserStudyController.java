package com.studymate.controller.userstudy;

import com.studymate.dto.userstudy.StudyMemberResponseDto;
import com.studymate.dto.userstudy.UserStudyRequestDto;
import com.studymate.dto.userstudy.UserStudyResponseDto;
import com.studymate.service.userstudy.UserStudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserStudyController {

    private final UserStudyService userStudyService;

    @PostMapping("/user-studies")
    public ResponseEntity<UserStudyResponseDto> joinStudy(@Valid @RequestBody UserStudyRequestDto dto){
        UserStudyResponseDto response =  userStudyService.joinStudy(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/user-studies/my")
    public ResponseEntity<List<UserStudyResponseDto>> getMyStudies(){
        return ResponseEntity.ok(userStudyService.getMyStudies());
    }

    @GetMapping("/studies/{studyNum}/members")
    public ResponseEntity<List<StudyMemberResponseDto>> getStudyMembers(@PathVariable Long studyNum){
        return ResponseEntity.ok(userStudyService.getStudyMembers(studyNum));
    }

    @DeleteMapping("/user-studies/{userStudyNum}")
    public ResponseEntity<Void> cancelStudy(@PathVariable Long userStudyNum){
        userStudyService.cancelStudy(userStudyNum);
        return ResponseEntity.noContent().build(); //204
    }
}
