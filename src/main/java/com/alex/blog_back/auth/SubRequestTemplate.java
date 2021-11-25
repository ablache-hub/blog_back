package com.alex.blog_back.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubRequestTemplate {
    private String username;
    private String name;
    private String password;
}

