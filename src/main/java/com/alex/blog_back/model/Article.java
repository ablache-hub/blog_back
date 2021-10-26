package com.alex.blog_back.model;

import com.alex.blog_back.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titre;
    @Column(length = 2000)
    private String contenu;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn
    private AppUser auteur;

    @ManyToOne
    @JoinColumn
    private Categorie categorie;

    public Article(Long id, String titre, String contenu, AppUser auteur, Categorie categorie) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.date = java.time.LocalDateTime.now();
        this.auteur = auteur;
        this.categorie = categorie;
    }
}
