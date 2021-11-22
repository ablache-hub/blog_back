package com.alex.blog_back.controller;

import com.alex.blog_back.model.Article;

import com.alex.blog_back.model.ArticleRequestTemplate;
import com.alex.blog_back.service.ArticleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    final private ArticleServiceImpl articleService;

    //GET all articles
    @GetMapping("/get/all")
    ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(
                articleService.findAllArticle()
        );
    }

    //GET Article par Id
    @GetMapping("/get/{id}")
    ResponseEntity<Optional<Article>> findArticleById(
            @PathVariable Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id));
    }

    //GET articles par catégorie
    @GetMapping("/get/allByCategorie/{categorie}")
    ResponseEntity<Optional<List<Article>>> findAllArticleByCategorie(
            @PathVariable("categorie") String categorie) {
        return ResponseEntity.ok().body(articleService.findAllArticleByCategorieServ(categorie));
    }

    // ADD Nouvel Article avec auteur et catégorie
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @PostMapping("/new/")
    ResponseEntity<?> addArticleByAuteur(
            @ModelAttribute ArticleRequestTemplate model) throws IllegalAccessException, IOException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurNamePicture(model));
    }

    //UPDATE Article
    @PutMapping("auteur/{username}/modify")
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    ResponseEntity<Article> modifyArticle(@PathVariable String username,
                                          @RequestBody Article article,
                                          @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.modifyArticle(username, article, categorie));
    }

    //DEL Article
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @DeleteMapping("auteur/{username}/delete/{id}")
    ResponseEntity<Article> deleteArticleByAuthorAndId(
            @PathVariable(value = "username") String username,
            @PathVariable(value = "id") Long id) throws IllegalAccessException {
        articleService.deleteArticleByAuthorAndIdService(username, id);
        return ResponseEntity.accepted().build();
    }

  /*    //ADD Article
    @PostMapping
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    ResponseEntity<Article> addArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.addArticle(article));
    }*/


    // POST Nouvel Article avec auteur et catégorie
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @PostMapping("/{username}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article,
                                                 @PathVariable String username,
                                                 @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurName(article, username, categorie));
    }


}
