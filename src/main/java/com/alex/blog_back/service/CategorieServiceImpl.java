package com.alex.blog_back.service;

import com.alex.blog_back.model.Categorie;
import com.alex.blog_back.repo.CategorieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.module.FindException;
import java.util.List;

@Service
@Configurable
@Transactional
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepo categorieRepo;

    @Override
    public List<Categorie> getAll() {
        return categorieRepo.findAll();
    }

    @Override
    public Categorie newCategorie(Categorie categorie) {
        if (categorieRepo.findCategorieByNom(categorie.getNom()).isEmpty()) {
            return categorieRepo.save(categorie);
        } else throw new FindException("Catégorie déjà existante");
    }
}
