package com.alex.blog_back.controller;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;
import com.alex.blog_back.repo.CategorieRepo;
import com.alex.blog_back.service.ArticleService;
import com.alex.blog_back.service.ArticleServiceImpl;
import com.alex.blog_back.service.CategorieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/categorie")
public class CategorieController {

    final private CategorieServiceImpl categorieService;

    @GetMapping("/getAll")
    List<Categorie> getAllCategorie() {
        return categorieService.getAll();
    }

    @PostMapping("/newCategorie/")
    ResponseEntity<Categorie> addCategory(@RequestBody Categorie categorie) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/article/newCategorie").toUriString());
        return ResponseEntity.created(uri).body(categorieService.newCategorie(categorie));
    }


}
