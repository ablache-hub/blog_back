package com.alex.blog_back.model;

import com.alex.blog_back.auth.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titre;
    @Column(length = 2000)
    private String contenu;
    private String date;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"articles"})
    private AppUser auteur;

    @ManyToOne
    @JoinColumn
    private Categorie categorie;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_picture_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"appUser", "data", "article"})
    private ProfilPic articlePicture;

    public Article(Long id, String titre, String contenu, AppUser auteur, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.categorie = categorie;
    }

}
