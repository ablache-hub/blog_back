package com.alex.blog_back.controller;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.RoleToUserTemplate;
import com.alex.blog_back.auth.SubRequestTemplate;
import com.alex.blog_back.service.AppUserService;
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
public class AppUserController {
    private final AppUserService appUserService;

    // GET ALL Users
    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    // GET user par username
    @GetMapping("/get/{username}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<AppUser> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(appUserService.getUser(username));
    }

    //GET Credentials/infos compte
    @GetMapping("/myCredentials")
    public ResponseEntity<AppUser> getCredentials() {
        return ResponseEntity.ok().body(appUserService.getCredentials());
    }

    // Inscription nouvel utilisateur
    @PostMapping("/subscribe")
    public ResponseEntity<AppUser> saveUser(@RequestBody SubRequestTemplate subUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newUser(subUser));
    }

    // POST NEW Role
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.newRole(role));
    }

    // POST/ADD Role -> User
    @PostMapping("/role/addroleuser")
    public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUserTemplate role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/addroleuser").toUriString());
        appUserService.addRoleToUser(role.getUsername(), role.getRoleName());
        return ResponseEntity.created(uri).build();
    }

    //PUT/UPDATE User Profil
    @PutMapping("{username}")
    public ResponseEntity<AppUser> updateUser(@PathVariable String username,
                                              @RequestBody AppUser user) throws IllegalAccessException {
        return ResponseEntity.ok().body(appUserService.updateUser(username, user));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteUser() {
        return ResponseEntity.accepted().body(appUserService.deleteUser());
    }
}

