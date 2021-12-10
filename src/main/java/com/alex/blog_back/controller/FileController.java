package com.alex.blog_back.controller;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.message.ResponseFile;
import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.ProfilPic;
import com.alex.blog_back.service.AppUserService;
import com.alex.blog_back.service.ArticleService;
import com.alex.blog_back.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/file")
@CrossOrigin
@Api(
        value = "/file",
        tags = "Contrôleur Images",
        description = "Contrôleur des images",
        produces = "application/json")
public class FileController {

    private final FileStorageService storageService;
    private final AppUserService appUserService;
    private final ArticleService articleService;

    @GetMapping("/getAll")
    @ApiOperation(value = "Liste de toutes les images")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/file/getAll/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/getById/{id}")
    @ApiOperation(value = "Obtenir l'image par son Id")
    public ResponseEntity<byte[]> getFile(
            @ApiParam(name = "id",
                    type = "Long",
                    value = "Id de l'image souhaitée",
                    example = "") @PathVariable String id) {
        ProfilPic fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @PostMapping("/user/{username}/upload")
    @ApiOperation(value = "Ajoute une image à l'utilisateur authentifié")
    public ResponseEntity<AppUser> addPicToUser(
            @ApiParam(name = "file",
                    type = "MultipartFile",
                    value = "L'image à ajouter au compte de l'utilisateur authentifié") @RequestParam("file") MultipartFile file,
            @PathVariable String username) throws IOException {

        return ResponseEntity.ok().body(appUserService.addProfilpicToUser(file, username));
       /* String message = "";
        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }*/
    }

    @PostMapping("/article/{articleid}/upload")
    @ApiOperation(value = "Ajoute une image à un article par son Id")
    public ResponseEntity<Article> addPicToArticle(
            @ApiParam(name = "file",
                    type = "MultipartFile",
                    value = "Image à ajouter à l'article") @RequestParam("file") MultipartFile file,
            @ApiParam(name = "articleId",
                    type = "Long",
                    value = "Id de l'article auquel on ajoute l'image",
                    example = "3") @PathVariable Long articleid) throws IOException {

        return ResponseEntity.ok().body(articleService.addPictureToArticle(file, articleid));
    }

/*    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/
}