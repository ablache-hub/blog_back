package com.alex.blog_back;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.SubRequestTemplate;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.service.AppUserService;
import com.alex.blog_back.service.ArticleService;
import com.alex.blog_back.service.CategorieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class BlogBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(AppUserService appUserService, ArticleService articleService, CategorieService categorieService, AppUserRepo appUserRepo) {
//        return args -> {
////            Collection<Role> list = new LinkedList<>(
////                    List.of(
////                            new Role(1L, "ROLE_AUTEUR"))
////            );
//
//            appUserService.newRole(new Role(null, "ROLE_AUTEUR"));
//            appUserService.newRole(new Role(null, "ROLE_ADMIN"));
//            appUserService.newRole(new Role(null, "ROLE_LECTEUR"));
//
//            appUserRepo.save(
//                    new AppUser(
//                            1L,
//                            null,
//                            "test1@gmail.com",
//                            "$2a$10$boRKAExW7SyVAfu0syND5u8it0q8pi5O1HY5HuGagg50Z7TAFttoe",
//                            null,
//                            List.of(
//                                    new Role(1L, "ROLE_AUTEUR")))
//            );
//
//            categorieService.newCategorie(new Categorie(null, "Sport", null));
//            categorieService.newCategorie(new Categorie(null, "Santé", null));
//            categorieService.newCategorie(new Categorie(null, "Cinema", null));
//            categorieService.newCategorie(new Categorie(null, "Nutrition", null));
//            categorieService.newCategorie(new Categorie(null, "Technologies", null));
//
//            appUserService.newUser(new SubRequestTemplate("test_Lecteur@gmail.com", "test"));
//            appUserService.newUser(new SubRequestTemplate("test_Auteur@gmail.com", "test"));
//            appUserService.newUser(new SubRequestTemplate("test_Admin@gmail.com", "test"));
//
//            appUserService.addRoleToUser("test_Lecteur@gmail.com", "ROLE_LECTEUR");
//            appUserService.addRoleToUser("test_Auteur@gmail.com", "ROLE_AUTEUR");
//            appUserService.addRoleToUser("test_Admin@gmail.com", "ROLE_ADMIN");
//
//
//            articleService.addArticle(new Article(null, "Mon premier article", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", null, null));
//            articleService.addArticle(new Article(null, "Lorem test", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", null, null));
//
//
//        };
//    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
