package com.alex.blog_back.service;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findArticleById(Long id);

    List<Article> findAllArticle();

    Article addArticle(Article article);

    void updateArticle(Long id, Article article);

    void deleteArticleByAuthorAndIdService(String username, Long id) throws IllegalAccessException;

    Article addArticleWithAuteurName(Article article, String username) throws IllegalAccessException;

    Categorie newCategorie(Categorie categorie);

    Optional<List<Article>> findAllArticleByCategorieServ(String categorie);

//    Article addArticleWithAuteurId(Article article, Long idAuteur);

}
