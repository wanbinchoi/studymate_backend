package com.studymate.controller.usercategory;

import com.studymate.dto.usercategory.UserCategoryRequestDto;
import com.studymate.dto.usercategory.UserCategoryResponseDto;
import com.studymate.service.usercategory.UserCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/categories")
@RequiredArgsConstructor
public class UserCategoryController {

    private final UserCategoryService userCategoryService;

    @PostMapping
    public ResponseEntity<String> addUserCategory(@RequestBody UserCategoryRequestDto dto){
        userCategoryService.addUserCategory(dto);
        return ResponseEntity.ok("카테고리가 등록되었습니다.");
    }

    @DeleteMapping("/{cgNum}")
    public ResponseEntity<String> deleteUserCategory(@PathVariable Long cgNum){
        userCategoryService.deleteUserCategory(cgNum);
        return ResponseEntity.ok("카테고리가 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<UserCategoryResponseDto>> getUserCategories(){
        List<UserCategoryResponseDto> userCategories = userCategoryService.getUserCategories();
        return ResponseEntity.ok(userCategories);
    }

}
