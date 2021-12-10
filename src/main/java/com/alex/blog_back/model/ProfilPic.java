package com.alex.blog_back.model;

import javax.persistence.*;

import com.alex.blog_back.auth.AppUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "profil_pic")
public class ProfilPic {
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

    private String name;
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