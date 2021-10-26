package com.alex.blog_back.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleToUserTemplate {
    private String username;
    private String roleName;
}
