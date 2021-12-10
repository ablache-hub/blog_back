package com.alex.blog_back.repo;

import com.alex.blog_back.model.Categorie;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findCategorieByNom(String nom);
}
