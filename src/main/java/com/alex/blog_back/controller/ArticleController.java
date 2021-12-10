package com.alex.blog_back.controller;

import com.alex.blog_back.model.Article;

import com.alex.blog_back.model.ArticleRequestTemplate;
import com.alex.blog_back.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(
        value = "/article",
        tags = "Contrôleur Article",
        description = "Contrôleur des articles",
        produces = "application/json")
public class ArticleController {
    final private ArticleService articleService;

    //FETCH/GET all articles
    @GetMapping("/get/all")
    @ApiOperation(value = "Reqûete GET de la liste de tous les articles", responseContainer = "List")
    ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(
                articleService.findAllArticle()
        );
    }

    //FETCH/GET Article par Id
    @GetMapping("/get/{id}")
    @ApiOperation(value = "Requête GET d'un article par son Id unique")
    ResponseEntity<Optional<Article>> findArticleById(
            @ApiParam(name = "Id d'un article",
                    type = "Long",
                    value = "L'id de l'article voulu",
                    example = "5") @PathVariable Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id));
    }

    //FETCH/GET articles par catégorie
    @GetMapping("/get/allByCategorie/{categorie}")
    @ApiOperation(value = "Get la liste des articles par catégorie")
    ResponseEntity<Optional<List<Article>>> findAllArticleByCategorie(
            @ApiParam(name = "categorie",
                    type = "String",
                    value = "Une catégorie existante",
                    example = "Sport") @PathVariable("categorie") String categorie) {
        return ResponseEntity.ok().body(articleService.findAllArticleByCategorieServ(categorie));
    }

    // POST/ADD Nouvel Article
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @PostMapping("/new/")
    @ApiOperation(value = "Enregistrement d'un nouvel article",
            notes = "La requête fonctionne avec ou sans adjonction d'image")
    ResponseEntity<?> addArticleByAuteur(
            @ApiParam(name = "Article",
                    type = "ArticleRequestTemplate",
                    value = "Un article avec ou sans image",
                    example = "Sport") @ModelAttribute ArticleRequestTemplate model) throws IllegalAccessException, IOException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurNamePicture(model));
    }

    //UPDATE/PUT Article
    @PutMapping("auteur/{username}/modify")
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @ApiOperation(value = "Modification d'un article",
            notes = "L'utilisateur authentifié doit impérativement être l'auteur de l'article à modifier, sinon la requête sera bloquée")
    ResponseEntity<Article> modifyArticle(@PathVariable String username,
                                          @ApiParam(
                                                  name = "Article MàJ",
                                                  type = "Article",
                                                  value = "La reqûete doit contenir la MàJ de l'article sous forme de modèle d'article",
                                                  required = true) @RequestBody Article article,
                                          @ApiParam(name = "categorie",
                                                  type = "String",
                                                  value = "Une catégorie existante",
                                                  example = "Sport") @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.modifyArticle(username, article, categorie));
    }

    //DEL Article
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @DeleteMapping("auteur/{username}/delete/{id}")
    @ApiOperation(value = "Suppression d'un article",
            notes = "L'utilisateur authentifié doit impérativement être l'auteur de l'article à supprimer, sinon la requête sera bloquée")
    ResponseEntity<Article> deleteArticleByAuthorAndId(
            @PathVariable(value = "username") String username,
            @ApiParam(
                    name = "id",
                    type = "Long",
                    value = "Id de l'article à supprimer",
                    required = true) @PathVariable(value = "id") Long id) throws IllegalAccessException {
        articleService.deleteArticleByAuthorAndIdService(username, id);
        return ResponseEntity.accepted().build();
    }

  /*    //ADD Article
    @PostMapping
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    ResponseEntity<Article> addArticle(@RequestBody Article article) {
        return ResponseEntity.ok().body(articleService.addArticle(article));
    }*/


  /*  // POST Nouvel Article avec auteur et catégorie
    @PreAuthorize("hasRole('ROLE_AUTEUR')")
    @PostMapping("/{username}")
    ResponseEntity<Article> addArticleWithAuteur(@RequestBody Article article,
                                                 @PathVariable String username,
                                                 @RequestParam(required = false) String categorie) throws IllegalAccessException {
        return ResponseEntity.ok().body(articleService.addArticleWithAuteurName(article, username, categorie));
    }*/


}
