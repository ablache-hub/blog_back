package com.alex.blog_back.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Wrapper du contenu de la réponse à une requête GET pour un fichier/image")
@AllArgsConstructor
@Getter
@Setter
public class ResponseFile {
    @ApiModelProperty(notes = "Nom du fichier",
            example = "profil.png")
    private String name;

    @ApiModelProperty(notes = "URL du fichier",
            example = "png")
    private String url;

    @ApiModelProperty(notes = "Type du fichier",
            example = "png")
    private String type;

    @ApiModelProperty(notes = "Taille du fichier",
            example = "65465432")
    private long size;
}