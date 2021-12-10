package com.alex.blog_back.controller;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.RoleToUserTemplate;
import com.alex.blog_back.auth.SubRequestTemplate;
import com.alex.blog_back.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
@Api(
        value = "/api/user",
        tags = "Contrôleur Utilisateurs",
        description = "Contrôleur des utilisateurs",
        produces = "application/json")
public class AppUserController {
    private final AppUserService appUserService;

    // GET ALL Users
    @GetMapping("/all")
    @ApiOperation(value = "GET liste de tous les utilisateurs")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    // GET user par username
    @GetMapping("/get/{username}")
    @ApiOperation(value = "Récupére un utilisateur par son pseudo/identifiant")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<AppUser> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(appUserService.getUser(username));
    }

    //GET Credentials/infos compte
    @GetMapping("/myCredentials")
    @ApiOperation(value = "Récupére les infos de l'utilisateur authentifié")
    public ResponseEntity<AppUser> getCredentials() {
        return ResponseEntity.ok().body(appUserService.getCredentials());
    }

    // Inscription nouvel utilisateur
    @PostMapping("/subscribe")
    @ApiOperation(value = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<AppUser> saveUser(
            @ApiParam(name = "subUser",
                    type = "SubRequestTemplate",
                    value = "Modèle d'utilisateur avec username, pseudo et mdp") @RequestBody SubRequestTemplate subUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newUser(subUser));
    }

    // POST NEW Role
    @PostMapping("/role/save")
    @ApiOperation(value = "Enregistrement d'un nouveau rôle")
    public ResponseEntity<Role> saveRole(
            @ApiParam(name = "Rôle",
                    type = "Role",
                    value = "Un rôle crée par son nom, avec un id auto-incrementé") @RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newRole(role));
    }

    // POST/ADD Role -> User
    @PostMapping("/role/addroleuser")
    @ApiOperation(value = "Ajout d'un rôle existant à un utilisateur")
    public ResponseEntity<Void> addRoleToUser(
            @ApiParam(name = "Modèle User/Rôle",
                    type = "RoleToUserTemplate.class",
                    value = "Un modèle sous forme de paire role/utilisateur") @RequestBody RoleToUserTemplate role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/addroleuser").toUriString());
        appUserService.addRoleToUser(role.getUsername(), role.getRoleName());
        return ResponseEntity.created(uri).build();
    }

    //PUT/UPDATE User Profil
    @PutMapping("{username}")
    @ApiOperation(value = "Update d'un utilisateur")
    public ResponseEntity<AppUser> updateUser(@PathVariable String username,
                                              @RequestBody AppUser user) throws IllegalAccessException {
        return ResponseEntity.ok().body(appUserService.updateUser(username, user));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "Suppression de l'utilisateur authentifié")
    public ResponseEntity<Void> deleteUser() {
        return ResponseEntity.accepted().body(appUserService.deleteUser());
    }
}

