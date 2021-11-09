package com.alex.blog_back.service;

import com.alex.blog_back.model.Categorie;

import java.util.List;

public interface CategorieService {
    List<Categorie> getAll();

    Categorie newCategorie(Categorie categorie);

}
