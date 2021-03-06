package com.alex.blog_back.service;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.ArticleRequestTemplate;
import com.alex.blog_back.model.Categorie;
import com.alex.blog_back.model.ProfilPic;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.repo.ArticleRepo;
import com.alex.blog_back.repo.CategorieRepo;
import com.alex.blog_back.repo.ProfilPicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepo articleRepo;
    private final AppUserRepo appUserRepo;
    private final CategorieRepo categorieRepo;
    private final ProfilPicRepository profilPicRepository;

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleRepo.findById(id);
    }

    @Override
    public List<Article> findAllArticle() {
        return articleRepo.findAll();
    }

    @Override
    public void deleteArticleByIdService(Long id) throws IllegalAccessException {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("Cet article n'éxiste pas"));

        if (!username.equals(article.getAuteur().getUsername())) {
            throw new IllegalAccessException("Accès non autorisé");
        } else {
            articleRepo.deleteById(id);
        }
    }

    @Override
    public Article addArticleWithAuteurNamePicture(ArticleRequestTemplate model) throws IllegalAccessException, IOException {
        //Vérification username nouvel article == username authentifié
        if (!Objects.equals(
                model.getUsername(),
                SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw new IllegalAccessException("Mauvais utilisateur");
        }
//        ProfilPic FileDB = new ProfilPic(newFileName, model.getPicture().getContentType(), model.getPicture().getBytes());

        Article currentArticle = new Article(
                null,
                model.getTitre(),
                model.getContenu(),
                appUserRepo.findByUsername(model.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, aucun auteur défini")),
                categorieRepo.findCategorieByNom(model.getCategorie())
                        .orElse(null));

        DateFormat mediumDateFormat = (DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM));
        String date = mediumDateFormat.format(new Date());
        currentArticle.setDate(
                ("Le " + date.substring(0, date.length() - 3))
                        .replace(":", "h")
        );

//        String newFileName = StringUtils.cleanPath(Objects.requireNonNull(
//                model.getPicture().getOriginalFilename()));
        if (model.getPicture() != null) {
            currentArticle.setArticlePicture(new ProfilPic(
                    model.getPicture().getOriginalFilename(),
                    model.getPicture().getContentType(),
                    model.getPicture().getBytes()));
        }

        return articleRepo.save(currentArticle);
    }


    @Override
    public Optional<List<Article>> findAllArticleByCategorieServ(String categorie) {
        Categorie currentCategorie = categorieRepo.findCategorieByNom(categorie)
                .orElseThrow(() -> new NullPointerException("Cette catégorie n'existe pas"));
        return articleRepo.findByCategorie(currentCategorie);
    }

    @Override
    public Article modifyArticle(Article newArticle, String categorie) throws IllegalAccessException {
        Article currentArticle = articleRepo.findById(newArticle.getId())
                .orElseThrow(() -> new NullPointerException("Cet article n'existe pas"));

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(currentArticle.getAuteur().getUsername())) {
            throw new IllegalAccessException("Accès non autorisé");
        }

        if (newArticle.getTitre() != null) {
            currentArticle.setTitre(newArticle.getTitre());
        }
        if (newArticle.getContenu() != null) {
            currentArticle.setContenu(newArticle.getContenu());
        }
        if (categorie != null) {
            currentArticle.setCategorie(categorieRepo.findCategorieByNom(categorie)
                    .orElseThrow(() -> new NullPointerException("Cette catégorie n'existe pas")));
        }

        DateFormat mediumDateFormat = (DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM));
        String date = mediumDateFormat.format(new Date());
        currentArticle.setDate(
                ("Modifié le " + date.substring(0, date.length() - 3))
                        .replace(":", "h")
        );

        return articleRepo.save(currentArticle);
    }


    @Override
    public Article addPictureToArticle(MultipartFile file, Long articleId) throws IOException {
        Article currentArticle = articleRepo.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, article inexistant"));

        //Suppression de l'ancienne image
        if (currentArticle.getArticlePicture() != null) {
            profilPicRepository.delete(currentArticle.getArticlePicture());
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        currentArticle.setArticlePicture(
                new ProfilPic(
                        fileName,
                        file.getContentType(),
                        file.getBytes())
        );
        return articleRepo.save(currentArticle);
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

      /*  @Override
    public Article addArticleWithAuteurName(Article article, String username, String categorie) throws IllegalAccessException {
        //Vérification username nouvel article == username authentifié
        if (!Objects.equals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw new IllegalAccessException("Mauvais utilisateur");
        }

        //Verif existance username puis ajout dans l'article
        article.setAuteur(
                appUserRepo.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, aucun auteur défini"))
        );

        //Verif existance categorie puis ajout dans l'article
        article.setCategorie(
                categorieRepo.findCategorieByNom(categorie)
                        .orElse(null));


        //Enreg. date de création article
        DateFormat mediumDateFormat = (DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM));
        String date = mediumDateFormat.format(new Date());
        article.setDate(
                ("Le " + date.substring(0, date.length() - 3))
                        .replace(":", "h")
        );
        return articleRepo.save(article);
    }
*/
}
