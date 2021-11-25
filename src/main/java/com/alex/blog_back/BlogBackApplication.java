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

    @Bean
    CommandLineRunner run(AppUserService appUserService, ArticleService articleService, CategorieService categorieService) {
        return args -> {
//            appUserService.newRole(new Role("ROLE_AUTEUR"));
//            appUserService.newRole(new Role("ROLE_ADMIN"));
//            appUserService.newRole(new Role(null, "ROLE_LECTEUR"));
//
//            categorieService.newCategorie(new Categorie("Cinema"));
//            categorieService.newCategorie(new Categorie("Cuisine"));
//            categorieService.newCategorie(new Categorie("Santé"));
//            categorieService.newCategorie(new Categorie("Sport"));
//            categorieService.newCategorie(new Categorie("Technologie"));
//
//            appUserService.newUser(new SubRequestTemplate("lecteur@gmail.com", "test"));
//            appUserService.newUser(new SubRequestTemplate("auteur@gmail.com", "test"));
//            appUserService.newUser(new SubRequestTemplate("admin@gmail.com", "test"));
//
//            appUserService.addRoleToUser("lecteur@gmail.com", "ROLE_LECTEUR");
//            appUserService.addRoleToUser("auteur@gmail.com", "ROLE_AUTEUR");
//            appUserService.addRoleToUser("admin@gmail.com", "ROLE_ADMIN");

        };
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
