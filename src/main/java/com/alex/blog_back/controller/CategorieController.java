package com.alex.blog_back.controller;

import com.alex.blog_back.model.Categorie;

import com.alex.blog_back.service.CategorieServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/categorie")
@Api(
        value = "/api/categorie",
        tags = "Contrôleur Catégories",
        description = "Contrôleur des catégories",
        produces = "application/json")
public class CategorieController {

    final private CategorieServiceImpl categorieService;

    @GetMapping("/getAll")
    @ApiOperation(value = "GET - List des catégories")
    List<Categorie> getAllCategorie() {
        return categorieService.getAll();
    }

    @PostMapping("/newCategorie/")
    @ApiOperation(value = "POST - Ajout d'un catégorie")
    ResponseEntity<Categorie> addCategory(
            @ApiParam(name = "Catégorie",
                    type = "String",
                    value = "Catégorie à créer par son nom",
                    example = "Sport") @RequestBody Categorie categorie) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/article/newCategorie").toUriString());
        return ResponseEntity.created(uri).body(categorieService.newCategorie(categorie));
    }


}
