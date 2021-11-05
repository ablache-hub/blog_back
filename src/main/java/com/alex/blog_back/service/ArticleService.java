package com.alex.blog_back.service;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findArticleById(Long id);

    List<Article> findAllArticle();

    List<Categorie> findAllCategorie();

    Article addArticle(Article article);

    void updateArticle(Long id, Article article);

    void deleteArticleByAuthorAndIdService(String username, Long id) throws IllegalAccessException;

    Article addArticleWithAuteurName(Article article, String username, String categorie) throws IllegalAccessException;


    Optional<List<Article>> findAllArticleByCategorieServ(String categorie);

//    Article addArticleWithAuteurId(Article article, Long idAuteur);

}
