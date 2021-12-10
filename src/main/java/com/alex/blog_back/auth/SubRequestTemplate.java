package com.alex.blog_back.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Wrapper d'une requête d'enregistrement d'un nouvel utilisateur")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubRequestTemplate {

    @ApiModelProperty(notes = "Intitulé du nouveau compte utilisateur",
            example = "auteur@mail.com")
    private String username;

    @ApiModelProperty(notes = "Nom/pseudo du nouvel utilisateur",
            example = "user1")
    private String name;

    @ApiModelProperty(notes = "Mot de passe à associer au compte",
            example = "kjhkjgtyufyu")
    private String password;
}

