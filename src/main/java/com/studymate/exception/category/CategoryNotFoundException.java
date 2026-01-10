package com.studymate.exception.category;

import com.studymate.exception.ErrorCode;
import com.studymate.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
    
    public CategoryNotFoundException(Long categoryId) {
        super(ErrorCode.CATEGORY_NOT_FOUND,
              String.format("카테고리를 찾을 수 없습니다. (categoryId: %d)", categoryId));
    }
}