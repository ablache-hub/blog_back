package com.alex.blog_back.model;

import javax.persistence.*;

import com.alex.blog_back.auth.AppUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@ApiModel(description = "Classe représentant un fichier (dans notre cas une image) dans l'application.")
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "profil_pic")
public class ProfilPic {
    @ApiModelProperty(notes = "Identifiant unique du fichier",
            example = "5")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

/*    private String uuid;

   @PrePersist
    public void initializeUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }*/

    @ApiModelProperty(notes = "Nom du fichier",
            example = "profil.png")
    private String name;

    @ApiModelProperty(notes = "Type du fichier",
            example = "png")
    private String type;

    @OneToOne(mappedBy = "profilePicture")
    @ApiModelProperty(
            value = "Utilisateur unique associé à l'image (objet de type 'AppUser.class', relation OneToOne)",
            dataType = "AppUser")
    private AppUser appUser;

    @OneToOne(mappedBy = "articlePicture")
    @ApiModelProperty(
            value = "Article unique associé à l'image (objet de type 'Article.class', relation OneToOne)",
            dataType = "Article")
    private Article article;

    @Lob
    private byte[] data;

    public ProfilPic(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}