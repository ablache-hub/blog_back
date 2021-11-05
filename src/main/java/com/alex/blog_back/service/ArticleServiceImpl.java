package com.alex.blog_back.service;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.Categorie;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.repo.ArticleRepo;
import com.alex.blog_back.repo.CategorieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.FindException;
import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepo articleRepo;
    private final AppUserRepo appUserRepo;
    private final CategorieRepo categorieRepo;

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleRepo.findById(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepo.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public void updateArticle(Long id, Article article) {

    }

    @Override
    public void deleteArticleByAuthorAndIdService(String username, Long id) throws IllegalAccessException {
        Article article = articleRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("Cet article n'éxiste pas"));
        if (username.equals(article.getAuteur().getUsername()) &&
                username.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        ) {
            articleRepo.deleteById(id);
        } else
            throw new IllegalAccessException("Action non autorisée pour cet utilisateur");

    }

    @Override
    public Article addArticleWithAuteurName(Article article, String username) throws IllegalAccessException {
        if (!Objects.equals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw new IllegalAccessException("Mauvais utilisateur");
        }
        AppUser testUser = appUserRepo.findByUsername(username).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, aucun auteur défini"));

        DateFormat mediumDateFormat = (DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM));
        String date = mediumDateFormat.format(new Date());
        article.setDate(
                ("Le " + date.substring(0, date.length() - 3))
                        .replace(":", "h")
        );

        article.setAuteur(testUser);
        return articleRepo.save(article);
    }

    @Override
    public Categorie newCategorie(Categorie categorie) {
        if (categorieRepo.findCategorieByNom(categorie.getNom()).isEmpty()) {
            return categorieRepo.save(categorie);
        } else throw new FindException("Catégorie déjà existante");
    }

    @Override
    public Optional<List<Article>> findAllArticleByCategorieServ(String categorie) {
        Categorie currentCategorie = categorieRepo.findCategorieByNom(categorie)
                .orElseThrow(() -> new NullPointerException("Cette catégorie n'existe pas"));
        return articleRepo.findByCategorie(currentCategorie);
    }

   /* @Override
    public Article addArticleWithAuteurId(Article article, Long idAuteur) {
        AppUser testUser = appUserRepo.findById(idAuteur).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, aucun d'auteur défini"));

        DateFormat mediumDateFormat = (DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM));
        String date = mediumDateFormat.format(new Date());
        article.setDate(
                ("Le " + date.substring(0, date.length() - 3))
                        .replace(":", "h")
        );

        article.setAuteur(testUser);

        return articleRepo.save(article);
    }*/

}
