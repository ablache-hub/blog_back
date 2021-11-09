package com.alex.blog_back.controller;

import com.alex.blog_back.model.Article;

import com.alex.blog_back.service.ArticleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<Optional<Article>> findArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id));
    }

    //GET articles par catégorie
    @GetMapping("/get/allByCategorie/{categorie}")
    ResponseEntity<Optional<List<Article>>> findAllArticleByCategorie(@PathVariable("categorie") String categorie) {
        return ResponseEntity.ok().body(articleService.findAllArticleByCategorieServ(categorie));
    }


    @PostMapping
    ResponseEntity<Article> addArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.addArticle(article));
    }


  /*  @PostMapping("/auteurid/{idAuteur}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article, @PathVariable Long idAuteur) {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurId(article, idAuteur));
    }*/

    // POST Nouvel Article avec auteur et catégorie
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @PostMapping("/{username}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article,
                                                 @PathVariable String username,
                                                 @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurName(article, username, categorie));
    }

    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @DeleteMapping("auteur/{username}/delete/{id}")
    ResponseEntity<Article> deleteArticleByAuthorAndId(
            @PathVariable(value = "username") String username,
            @PathVariable(value = "id") Long id) throws IllegalAccessException {
        articleService.deleteArticleByAuthorAndIdService(username, id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("auteur/{username}/modify")
    ResponseEntity<Article> modifyArticle(@PathVariable String username,
                                          @RequestBody Article article,
                                          @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.modifyArticle(username, article, categorie));
    }


}
