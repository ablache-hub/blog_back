package com.alex.blog_back.repo;

import com.alex.blog_back.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

}
