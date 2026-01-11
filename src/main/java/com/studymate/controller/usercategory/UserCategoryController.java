package com.studymate.controller.usercategory;

import com.studymate.dto.usercategory.UserCategoryRequestDto;
import com.studymate.dto.usercategory.UserCategoryResponseDto;
import com.studymate.service.usercategory.UserCategoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> addUserCategory(@Valid @RequestBody UserCategoryRequestDto dto){
        userCategoryService.addUserCategory(dto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{cgNum}")
    public ResponseEntity<Void> deleteUserCategory(@PathVariable Long cgNum){
        userCategoryService.deleteUserCategory(cgNum);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserCategoryResponseDto>> getUserCategories(){
        List<UserCategoryResponseDto> userCategories = userCategoryService.getUserCategories();
        return ResponseEntity.ok(userCategories);
    }

}
