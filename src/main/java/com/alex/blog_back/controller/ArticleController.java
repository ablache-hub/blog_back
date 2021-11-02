package com.alex.blog_back.controller;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.service.ArticleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    final private ArticleServiceImpl articleService;

    @GetMapping("/get/all")
    ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(
                articleService.findAllArticle()
        );
    }


    @GetMapping("/get/{id}")
    ResponseEntity<Optional<Article>> findArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id));
    }

    @PostMapping
    ResponseEntity<Article> addArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.addArticle(article));
    }


  /*  @PostMapping("/auteurid/{idAuteur}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article, @PathVariable Long idAuteur) {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurId(article, idAuteur));
    }*/

    @PostMapping("/{username}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article, @PathVariable String username) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurName(article, username));
    }
}
