package com.ecommerce.ecommerce_backend.config;

import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.Category;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.CategoryRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    public DataInitializer(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        // ✅ Check if admin already exists
        boolean adminExists = userRepository
                .existsByEmail("admin@ecommerce.com");

        if (!adminExists) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword("admin123"); // plain text (as per your design)
            admin.setRole(UserRole.ADMIN);
            admin.setSellerVerified(true); // irrelevant but safe

            userRepository.save(admin);

            System.out.println("✅ Dummy admin created: admin@ecommerce.com / admin123");
        }

        boolean sellerExists = userRepository
                .existsByEmail("seller@ecommerce.com");

        if (!adminExists) {
            User admin = new User();
            admin.setName("Seller");
            admin.setEmail("seller@ecommerce.com");
            admin.setPassword("seller123"); // plain text (as per your design)
            admin.setRole(UserRole.SELLER);
            admin.setSellerVerified(true); // irrelevant but safe

            userRepository.save(admin);

            System.out.println("✅ Dummy seller created: seller@ecommerce.com / seller123");
        }

        Category category = new Category();
        category.setName("Sports");
        category.setDescription("Cricket Bats");
        categoryRepository.save(category);
        System.out.println("✅ Dummy Category created");
    }
}
