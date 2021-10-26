package com.alex.blog_back.repo;

import com.alex.blog_back.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, Long> {
}
