package com.alex.blog_back.service;

import com.alex.blog_back.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> findArticleById(Long id);
    List<Article> findAllArticle();
    Article addArticle(Article article);
    void updateArticle(Long id, Article article);
    void deleteArticle(Long id);

    Article addArticleWithAuteur(Article article, Long idAuteur);
}
