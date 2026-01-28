package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.model.Category;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.CategoryRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCategoryService {
    private final AdminAuthUtil adminAuthUtil;
    private final CategoryRepository categoryRepository;

    public AdminCategoryService(AdminAuthUtil adminAuthUtil, CategoryRepository categoryRepository) {
        this.adminAuthUtil = adminAuthUtil;
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(User admin, Category category) {
        adminAuthUtil.validateAdmin(admin);
        return categoryRepository.save(category);
    }

    public Category updateCategory(User admin, Long categoryId, Category updated) {
        adminAuthUtil.validateAdmin(admin);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        category.setParentCategory(updated.getParentCategory());

        return categoryRepository.save(category);
    }

    public void deleteCategory(User admin, Long categoryId) {
        adminAuthUtil.validateAdmin(admin);
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getAllCategories(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return categoryRepository.findAll();
    }
}
