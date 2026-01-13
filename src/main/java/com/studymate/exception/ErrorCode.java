package com.studymate.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //400 Bad Request
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E001", "잘못된 입력값입니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "E002", "시작일은 종료일보다 이전이어야 합니다."),
    STUDY_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "E003", "스터디 정원이 초과되었습니다."),
    DUPLICATE_CATEGORY(HttpStatus.BAD_REQUEST, "E004", "중복된 카테고리를 선택할 수 없습니다."),
    ALREADY_JOINED(HttpStatus.BAD_REQUEST, "E005", "이미 참여한 스터디입니다."),
    ALREADY_REQUESTED(HttpStatus.BAD_REQUEST, "E006", "이미 신청한 스터디 입니다."),
    REQUEST_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "E007", "이미 처리된 신청입니다."),
    SELF_TRANSFER_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "E008", "본인에게는 방장 권한을 양도할 수 없습니다."),
    USER_NOT_MEMBER(HttpStatus.BAD_REQUEST, "E009", "해당 사용자는 스터디 멤버가 아닙니다."),
    SELF_KICK_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "E010", "자기 자신을 퇴출할 수 없습니다."),
    INVALID_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "E011", "잘못된 상태 전환입니다."),

    //401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E101", "인증이 유효하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E102", "유효하지 않는 토큰입니다."),

    //403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, "E201", "권한이 없습니다."),
    STUDY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "E202", "스터디를 수정/삭제할 권한이 없습니다."),

    //404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E301", "사용자를 찾을 수 없습니다."),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "E302", "스터디를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "E303", "카테고리를 찾을 수 없습니다."),
    USER_STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "E304", "참여 기록을 찾을 수 없습니다."),
    USER_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "E305", "신청 기록을 찾을 수 없습니다."),

    //500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E999", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
