package com.alex.blog_back.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Getter
@Setter
public class ArticleRequestTemplate {
    private String username;
    private String titre;
    private String contenu;
    private String categorie;
    private MultipartFile picture;

}


