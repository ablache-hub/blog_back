package com.alex.blog_back.repo;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    Optional<Article> findArticleByCategorie(Categorie categorie);

    Optional<List<Article>> findByCategorie(Categorie categorie);
}
