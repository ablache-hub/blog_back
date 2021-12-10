package com.alex.blog_back.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ApiModel(description = "Classe représentant un rôle d'utilisateur dans l'application.")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @ApiModelProperty(notes = "Identifiant unique du rôle",
            example = "5")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Intitulé du rôle",
            example = "5")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
