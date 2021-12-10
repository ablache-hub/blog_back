package com.alex.blog_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@ApiModel(description = "Classe représentant une catégorie dans l'application.")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identifiant unique de la catégorie",
            example = "5")
    Long id;

    @ApiModelProperty(notes = "Intitulé de la catégorie",
            example = "Santé")
    String nom;

    @JsonIgnore
    @OneToMany(mappedBy = "categorie")
    @ApiModelProperty(
            value = "Articles associés à la catégorie (objet type 'List Article', relation OneToMany)",
            dataType = "List<Article>")
    List<Article> articles;

    public Categorie(String nom) {
        this.nom = nom;
    }
}
