package com.alex.blog_back.model;

import javax.persistence.*;

import com.alex.blog_back.auth.AppUser;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    private AppUser appUser;

    @OneToOne(mappedBy = "articlePicture")
    private Article article;

    @Lob
    private byte[] data;

    public ProfilPic(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}