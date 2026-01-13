package com.studymate.controller.userrequest;

import com.studymate.dto.userrequest.StudyRequestResponseDto;
import com.studymate.dto.userrequest.UserRequestCreateDto;
import com.studymate.dto.userrequest.UserRequestResponseDto;
import com.studymate.service.userrequest.UserRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @PostMapping("/user-requests")
    public ResponseEntity<UserRequestResponseDto> createRequest(@Valid @RequestBody UserRequestCreateDto dto){
        UserRequestResponseDto response = userRequestService.createRequest(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/user-requests/my")
    public ResponseEntity<List<UserRequestResponseDto>> getMyRequests(){
        return ResponseEntity.ok(userRequestService.getMyRequests());
    }

    @GetMapping("/studies/{studyNum}/requests")
    public ResponseEntity<List<StudyRequestResponseDto>> getStudyRequests(@PathVariable Long studyNum){
        return ResponseEntity.ok(userRequestService.getStudyRequests(studyNum));
    }

    @PatchMapping("/user-requests/{reqNum}/approve")
    public ResponseEntity<StudyRequestResponseDto> approveRequest(@PathVariable Long reqNum){
        StudyRequestResponseDto response = userRequestService.approveRequest(reqNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user-requests/{reqNum}/reject")
    public ResponseEntity<StudyRequestResponseDto> rejectRequest(@PathVariable Long reqNum){
        StudyRequestResponseDto response = userRequestService.rejectRequest(reqNum);
        return ResponseEntity.ok(response);
    }

}
