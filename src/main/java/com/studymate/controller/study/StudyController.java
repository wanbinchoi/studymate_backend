package com.studymate.controller.study;

import com.studymate.dto.study.StudyCreateRequestDto;
import com.studymate.dto.study.StudyDetailResponseDto;
import com.studymate.dto.study.StudyListResponseDto;
import com.studymate.dto.study.StudyUpdateRequestDto;
import com.studymate.service.study.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<StudyDetailResponseDto> createStudy(@Valid @RequestBody StudyCreateRequestDto dto){
        StudyDetailResponseDto study = studyService.createStudy(dto);

        return ResponseEntity.status(201).body(study);
    }

    @GetMapping
    public ResponseEntity<Page<StudyListResponseDto>> getStudyList(Pageable pageable, @RequestParam(required = false) String keyword){
            Page<StudyListResponseDto> studies = studyService.getStudyList(pageable, keyword);

            return ResponseEntity.ok(studies);
    }

    @GetMapping("/{studyNum}")
    public ResponseEntity<StudyDetailResponseDto> getStudyDetail(@PathVariable Long studyNum){
        StudyDetailResponseDto studyDetail = studyService.getStudyDetail(studyNum);
        return ResponseEntity.ok(studyDetail);
    }

    @PatchMapping("/{studyNum}")
    public ResponseEntity<StudyDetailResponseDto> updateStudy(@PathVariable Long studyNum, @Valid @RequestBody StudyUpdateRequestDto dto){
        StudyDetailResponseDto updateStudy = studyService.updateStudy(studyNum, dto);
        return ResponseEntity.ok(updateStudy);
    }

    @DeleteMapping("/{studyNum}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long studyNum){
        studyService.deleteStudy(studyNum);

        return ResponseEntity.noContent().build();
    }
}
