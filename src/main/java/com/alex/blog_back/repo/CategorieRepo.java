package com.alex.blog_back.repo;

import com.alex.blog_back.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
}
