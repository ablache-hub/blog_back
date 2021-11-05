package com.alex.blog_back.repo;

import com.alex.blog_back.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findCategorieByNom(String nom);
}
