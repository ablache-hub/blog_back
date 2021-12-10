package com.alex.blog_back.model;

import com.alex.blog_back.auth.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@ApiModel(description = "Classe représentant un article dans l'application.")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article {
    @ApiModelProperty(notes = "Identifiant unique de l'article",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Titre de l'article",
            example = "Mon article")
    private String titre;
    @ApiModelProperty(notes = "Contenu de l'article",
            example = "Voici la contenu de cet article")
    @Column(length = 2000)
    private String contenu;
    @ApiModelProperty(notes = "Date de création de l'article",
            example = "25 mai 2005")
    private String date;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"articles"})
    @ApiModelProperty(
            value = "Auteur de l'article (objet de type 'AppUser.class', relation ManyToOne)",
            dataType = "AppUser")
    private AppUser auteur;

    @ManyToOne
    @JoinColumn
    @ApiModelProperty(
            value = "Catégorie de l'article (objet de type 'Categorie.class', relation ManyToOne)",
            dataType = "Categorie")
    private Categorie categorie;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_picture_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"appUser", "data", "article"})
    @ApiModelProperty(
            value = "Image de l'article (objet de type 'ProfilPic.class', relation OneToOne)",
            dataType = "ProfilPic")
    private ProfilPic articlePicture;

    public Article(Long id, String titre, String contenu, AppUser auteur, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.categorie = categorie;
    }

}
