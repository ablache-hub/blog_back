package com.alex.blog_back.model;

import com.alex.blog_back.auth.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    @Column(length = 2000)
    private String contenu;
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
