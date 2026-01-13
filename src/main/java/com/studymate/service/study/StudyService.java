package com.studymate.service.study;

import com.studymate.domain.category.Category;
import com.studymate.domain.study.Study;
import com.studymate.domain.studycategory.StudyCategory;
import com.studymate.domain.user.User;
import com.studymate.domain.userstudy.UserStudy;
import com.studymate.dto.study.*;
import com.studymate.exception.ErrorCode;
import com.studymate.exception.InvalidRequestException;
import com.studymate.exception.UnauthorizedException;
import com.studymate.exception.category.CategoryNotFoundException;
import com.studymate.exception.study.StudyAccessDeniedException;
import com.studymate.exception.study.StudyNotFoundException;
import com.studymate.exception.user.UserNotFoundException;
import com.studymate.repository.category.CategoryRepository;
import com.studymate.repository.study.StudyRepository;
import com.studymate.repository.studycategory.StudyCategoryRepository;
import com.studymate.repository.user.UserRepository;
import com.studymate.repository.userstudy.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final CategoryRepository categoryRepository;
    private final StudyCategoryRepository studyCategoryRepository;
    private final UserStudyRepository userStudyRepository;

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        return userRepository.findByUserId(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Transactional
    public StudyDetailResponseDto createStudy(StudyCreateRequestDto dto){
        User user = getCurrentUser();

        validateDateRange(dto.getStartDate(), dto.getEndDate());
        validateDuplicateCategories(dto.getCategoryIds());

        Study study = Study.builder()
                .studyTitle(dto.getStudyTitle())
                .studyDesc(dto.getStudyDesc())
                .maxMember(dto.getMaxMember())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .leaderId(user)
                .build();

        Study savedStudy = studyRepository.save(study);

        for(Long categoryId : dto.getCategoryIds()){
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException(categoryId));

            StudyCategory studyCategory = StudyCategory.builder()
                    .study(savedStudy)
                    .category(category)
                    .build();

            studyCategoryRepository.save(studyCategory);
        }

        List<StudyCategory> studyCategories = studyCategoryRepository.findByStudy_StudyNum(study.getStudyNum());
        int currentMember = userStudyRepository.countByStudy_StudyNum(savedStudy.getStudyNum());

        return StudyDetailResponseDto.from(savedStudy, studyCategories, currentMember);
    }

    @Transactional
    public StudyDetailResponseDto updateStudy(Long studyNum, StudyUpdateRequestDto dto){

        User user = getCurrentUser();

        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(studyNum, user.getUserId());
        }

        LocalDate newStartDate = dto.getStartDate() != null ? dto.getStartDate() : study.getStartDate();
        LocalDate newEndDate = dto.getEndDate() != null ? dto.getEndDate() : study.getEndDate();
        validateDateRange(newStartDate, newEndDate);

        if(dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()){
            validateDuplicateCategories(dto.getCategoryIds());
        }

        if (dto.getStudyTitle() != null) {
            study.setStudyTitle(dto.getStudyTitle());
        }

        if (dto.getStudyDesc() != null) {
            study.setStudyDesc(dto.getStudyDesc());
        }

        if (dto.getMaxMember() != null) {
            study.setMaxMember(dto.getMaxMember());
        }

        if (dto.getStudyStatus() != null) {
            study.setStudyStatus(dto.getStudyStatus());
        }

        if (dto.getStartDate() != null) {
            study.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            study.setEndDate(dto.getEndDate());
        }

        // 카테고리 수정 (리스트가 null이 아니고 비어있지 않을 때만)
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            // 기존 카테고리 삭제
            studyCategoryRepository.deleteByStudy_StudyNum(studyNum);
            studyCategoryRepository.flush();

            // 새 카테고리 추가
            for(Long categoryId : dto.getCategoryIds()){
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

                StudyCategory studyCategory = StudyCategory.builder()
                        .study(study)
                        .category(category)
                        .build();

                studyCategoryRepository.save(studyCategory);
            }
        }
        List<StudyCategory> studyCategories = studyCategoryRepository.findByStudy_StudyNum(studyNum);
        int currentMember = userStudyRepository.countByStudy_StudyNum(studyNum);

        return StudyDetailResponseDto.from(study, studyCategories, currentMember);
    }

    @Transactional
    public void deleteStudy(Long studyNum){
        User user = getCurrentUser();

        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(studyNum,user.getUserId());
        }
        studyCategoryRepository.deleteByStudy_StudyNum(studyNum);
        studyRepository.deleteById(studyNum);
    }

    @Transactional(readOnly = true)
    public Page<StudyListResponseDto> getStudyList(Pageable pageable, String keyword){

        Page<Study> studyPage;

        if(keyword != null && !keyword.isEmpty()){
            studyPage = studyRepository.findByStudyTitleContaining(keyword, pageable);
        } else{
            studyPage = studyRepository.findAll(pageable);
        }
        // 조회된 Study들의 StudyNum 목록 추출
        List<Long> studyNums = studyPage.getContent().stream()
                .map(Study::getStudyNum)
                .collect(Collectors.toList());

        // 빈 목록 체크(스터디가 없으면 빈 페이지 반환)
        if(studyNums.isEmpty()){
            return Page.empty(pageable);
        }

        // 모든 스터디의 카테고리를 한 번에 조회(JOIN FETCH로 Category도 함께)
        // 각 스터디별로 갖고 있는 카테고리 리스트 형성
        List<StudyCategory> allStudyCategories = studyCategoryRepository.findByStudyNumsWithCategory(studyNums);
        // 결과: [
        //   {studyNum=3, 백엔드}, {studyNum=3, 프론트엔드},
        //   {studyNum=4, 백엔드}, {studyNum=4, 프론트엔드}, {studyNum=4, 데브옵스}
        // ] - 섞여있는 상태


        // studyNum을 키로 하는 Map 생성
        // groupingBy를 통해서 studyNum과 위의 스터디별 카테고리를 통하여
        // 각 스터디의 스터디 번호와 그 스터디의 카테고리를 한 묶음 처럼 만들기 위함 -> 그룹화하여 빠르게 조회하기 위함
        Map<Long, List<StudyCategory>> studyCategoryMap = allStudyCategories.stream()
                .collect(Collectors.groupingBy(sc -> sc.getStudy().getStudyNum()));
        // 결과: {
        //   3: [{백엔드}, {프론트엔드}],
        //   4: [{백엔드}, {프론트엔드}, {데브옵스}]
        // } - studyNum으로 즉시 조회 가능 (O(1))

        Map<Long, Integer> memberCountMap = studyNums.stream()
                .collect(Collectors.toMap(
                        studyNum -> studyNum,
                        userStudyRepository::countByStudy_StudyNum
                ));

        //DTO로 변환
        Page<StudyListResponseDto> dtoPage = studyPage.map(study -> {
            List<StudyCategory> studyCategories =
                    studyCategoryMap.getOrDefault(study.getStudyNum(), List.of());
            //get()과 getOrDefault()
            // get은 없는 키값일 경우 null을 반환하여 NullPointerException 발생할 수도 있지만
            // getOrDefaul는 없는 값일 경우 빈 배열을 반환하여 안전하게 처리할 수 있다.

            Integer currentMember = memberCountMap.getOrDefault(study.getStudyNum(), 0);

            return StudyListResponseDto.from(study, studyCategories, currentMember);
        });

        return dtoPage;
    }

    @Transactional(readOnly = true)
    public StudyDetailResponseDto getStudyDetail(Long studyNum){
        Study study = studyRepository.findByIdWithLeader(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));
        List<StudyCategory> studyCategories = studyCategoryRepository.findByStudy_StudyNum(studyNum);

        int currentMember = userStudyRepository.countByStudy_StudyNum(studyNum);

        return StudyDetailResponseDto.from(study, studyCategories, currentMember);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate){
        if(startDate != null && endDate != null && startDate.isAfter(endDate)){
            throw new InvalidRequestException(
                    ErrorCode.INVALID_DATE_RANGE,
                    String.format("시작일(%s)은 종료일(%s)보다 이전이어야 합니다.", startDate, endDate));
        }
    }

    private void validateDuplicateCategories(List<Long> categoryIds){
        if(categoryIds == null || categoryIds.isEmpty()){
            return;
        }

        Set<Long> uniqueIds = new HashSet<>(categoryIds);
        if(uniqueIds.size() != categoryIds.size()){
            throw new InvalidRequestException(ErrorCode.DUPLICATE_CATEGORY);
        }
    }
    @Transactional
    public StudyDetailResponseDto transferLeader(Long studyNum, TransferLeaderRequestDto dto){
        User user = getCurrentUser();
        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        if(!study.getLeaderId().getUserId().equals(user.getUserId())){
            throw new StudyAccessDeniedException(studyNum, user.getUserId());
        }

        if (user.getUserId().equals(dto.getNewLeaderId())) {
            throw new InvalidRequestException(ErrorCode.SELF_TRANSFER_NOT_ALLOWED);
        }

        User transferUser = userRepository.findByUserId(dto.getNewLeaderId())
                .orElseThrow(() -> new UserNotFoundException(dto.getNewLeaderId()));
        UserStudy transferUserStudy = userStudyRepository.findByUser_UserNumAndStudy_StudyNum(transferUser.getUserNum(), studyNum)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.USER_NOT_MEMBER));

        boolean isCurrentLeaderMember = userStudyRepository.existsByUser_UserNumAndStudy_StudyNum(user.getUserNum(), studyNum);

        if(!isCurrentLeaderMember){
            UserStudy userStudy = UserStudy.builder()
                    .user(user)
                    .study(study)
                    .studyRole("MEMBER")
                    .build();
            userStudyRepository.save(userStudy);
        }

        study.setLeaderId(transferUser);

        transferUserStudy.setStudyRole("LEADER");

        List<StudyCategory> studyCategories =
                studyCategoryRepository.findByStudy_StudyNum(studyNum);
        int currentMember =
                userStudyRepository.countByStudy_StudyNum(studyNum);

        return StudyDetailResponseDto.from(study, studyCategories, currentMember);
    }
    public void validateStatusTransition(String currentStatus, String newStatus){

        if("COMPLETED".equals(currentStatus)){
            throw new InvalidRequestException(ErrorCode.INVALID_STATUS_TRANSITION);
        }

        if ("IN_PROGRESS".equals(currentStatus) && "RECRUITING".equals(newStatus)) {
            throw new InvalidRequestException(ErrorCode.INVALID_STATUS_TRANSITION);
        }
    }
    @Transactional
    public StudyDetailResponseDto updateStudyStatus(Long studyNum, UpdateStudyStatusDto dto){

        User user = getCurrentUser();

        Study study = studyRepository.findById(studyNum)
                .orElseThrow(() -> new StudyNotFoundException(studyNum));

        if(!study.getLeaderId().getUserNum().equals(user.getUserNum())){
            throw new StudyAccessDeniedException(studyNum, user.getUserId());
        }
        validateStatusTransition(study.getStudyStatus(), dto.getStatus());
        study.setStudyStatus(dto.getStatus());

        List<StudyCategory> studyCategories = studyCategoryRepository.findByStudy_StudyNum(studyNum);
        int currentMember = userStudyRepository.countByStudy_StudyNum(studyNum);

        return StudyDetailResponseDto.from(study, studyCategories, currentMember);

    }
}
