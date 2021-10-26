package com.alex.blog_back;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.SubRequestTemplate;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.service.AppUserService;
import com.alex.blog_back.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService appUserService, ArticleService articleService) {
        return args -> {
            appUserService.newRole(new Role(null, "ROLE_USER"));
            appUserService.newRole(new Role(null, "ROLE_ADMIN"));

            appUserService.newUser(new SubRequestTemplate("test@gmail.com", "test" ));
            appUserService.newUser(new SubRequestTemplate("test2@gmail.com", "test" ));


            appUserService.addRoleToUser("test2@gmail.com", "ROLE_ADMIN");

            articleService.addArticle(new Article(null,"Mon premier article", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",  null, null));
            articleService.addArticle(new Article(null,"Lorem test", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",  null, null));

        };
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
