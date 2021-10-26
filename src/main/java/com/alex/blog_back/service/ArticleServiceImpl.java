package com.alex.blog_back.service;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.repo.ArticleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepo articleRepo;
    private final AppUserRepo appUserRepo;

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleRepo.findById(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepo.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public void updateArticle(Long id, Article article) {

    }

    @Override
    public void deleteArticle(Long id) {

    }

    @Override
    public Article addArticleWithAuteur(Article article, Long idAuteur) {
        AppUser testUser = appUserRepo.findById(idAuteur).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, aucun d'auteur défini"));

        article.setAuteur(testUser);

        return articleRepo.save(article);
    }
}
