package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.category.CategoryRequest;
import com.ecommerce.ecommerce_backend.model.Category;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminCategoryService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService categoryService;
    private final AuthService authService;

    public AdminCategoryController(AdminCategoryService categoryService,
                                   AuthService authService) {
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @PostMapping
    public Category createCategory(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @RequestBody CategoryRequest cat) {
        Category category = new Category();
        category.setName(cat.getName());
        category.setDescription(cat.getDescription());
        User admin = authService.getUserById(adminId);
        return categoryService.createCategory(admin, category);
    }

    @GetMapping
    public List<Category> getAllCategories(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);
        return categoryService.getAllCategories(admin);
    }
}
