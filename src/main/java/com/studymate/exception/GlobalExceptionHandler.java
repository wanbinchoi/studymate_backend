package com.studymate.exception;

import com.studymate.dto.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){
       log.error("BusinessException 발생: {}, ", e.getMessage(), e);

       ErrorResponse response = ErrorResponse.builder()
               .status(e.getErrorCode().getStatus().value())
               .code(e.getErrorCode().getCode())
               .message(e.getErrorCode().getMessage()) //기본메세지만 제공
               .timestamp(LocalDateTime.now())
               .build();

       return ResponseEntity
               .status(e.getErrorCode().getStatus())
               .body(response);
   }

   @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
       log.error("예상치 못한 예외 발생: {}", e.getMessage(), e);

       ErrorResponse response = ErrorResponse.builder()
               .status(500)
               .code("E999")
               .message("서버 내부 오류가 발생했습니다.")
               .timestamp(LocalDateTime.now())
               .build();

       return ResponseEntity
               .status(500)
               .body(response);
   }

   //Bean Vlidation 실패 처리
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ErrorResponse> handleVlidationException(MethodArgumentNotValidException e){
      log.warn("검증 실패: {}", e.getMessage());

      String message = e.getBindingResult()
              .getAllErrors()
              .get(0)
              .getDefaultMessage();

      ErrorResponse response = ErrorResponse.builder()
              .status(400)
              .code("E001")
              .message(message)
              .timestamp(LocalDateTime.now())
              .build();

      return ResponseEntity.status(400).body(response);
   }
}
