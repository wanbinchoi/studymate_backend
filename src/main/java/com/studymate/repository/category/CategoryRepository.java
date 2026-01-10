package com.studymate.repository.category;

import com.studymate.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}

