package com.alex.blog_back.service;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.SubRequestTemplate;

import java.util.List;

public interface AppUserService {
    AppUser newUser(SubRequestTemplate subUser);
    Role newRole(Role role);
    void addRoleToUser(String username, String rolename);
    AppUser getUser(String username);
    List<AppUser> getUsers();

}
