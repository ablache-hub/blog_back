package com.alex.blog_back.service;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.ArticleRequestTemplate;
import com.alex.blog_back.model.Categorie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    Article modifyArticle(String username, Article article, String categorie) throws IllegalAccessException;

    Article addPictureToArticle(MultipartFile file, Long articleId) throws IOException;

    Article addArticleWithAuteurNamePicture(ArticleRequestTemplate model) throws IllegalAccessException, IOException;

//    Article addArticleWithAuteurId(Article article, Long idAuteur);

}
