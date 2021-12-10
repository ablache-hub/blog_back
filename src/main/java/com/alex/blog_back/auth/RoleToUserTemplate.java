package com.alex.blog_back.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Wrapper d'une requête pour l'attribution d'un rôle à un utilisateur")
@Getter
@Setter
@AllArgsConstructor
public class RoleToUserTemplate {
    @ApiModelProperty(notes = "Username de l'utilisateur visé",
            example = "auteur@mail.com")
    private String username;

    @ApiModelProperty(notes = "Rôle à attribuer à cet utilisateur",
            example = "ROLE_AUTEUR")
    private String roleName;
}
