package com.alex.blog_back.service;

import com.alex.blog_back.auth.AppUser;
import com.alex.blog_back.auth.Role;
import com.alex.blog_back.auth.SubRequestTemplate;
import com.alex.blog_back.model.ProfilPic;
import com.alex.blog_back.repo.AppUserRepo;
import com.alex.blog_back.repo.ProfilPicRepository;
import com.alex.blog_back.repo.RoleRepo;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.module.FindException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j // Log
public class AppUserImpl implements AppUserService, UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProfilPicRepository profilPicRepository;

    //TODO Completer "loadbyusername"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appuser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });*/
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + username + " n'existe pas"));
    }

    @Override
    public AppUser newUser(SubRequestTemplate subUser) {

        if (appUserRepo.findByUsername(subUser.getUsername()).isPresent()) {
            throw new FindException("Utilisateur existant");
        }

        AppUser currentUser = new AppUser();

        if (subUser.getUsername() != null) {
            currentUser.setUsername(subUser.getUsername());
        } else {
            throw new NullPointerException("Username requête null");
        }

        if (subUser.getName() != null) {
            currentUser.setName(subUser.getName());
        }

        if (subUser.getPassword() != null) {
            currentUser.setPassword(
                    passwordEncoder.encode(subUser.getPassword())
            );
        } else {
            throw new NullPointerException("Password requête null");
        }

        appUserRepo.save(currentUser);
        // Pour une version prod, on attribuerait ROLE_LECTEUR et il incomberait à l'admin d'ajouter ROLE_AUTEUR si besoin
        addRoleToUser(currentUser.getUsername(), "ROLE_AUTEUR");

        return currentUser;
    }

    @Override
    public Role newRole(Role role) {
        log.info("Enregistrement du nouveau role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, utilisateur inexistant"));
        Role role = roleRepo.findByName(rolename);
        appUser.getRoles().forEach(eachRole -> {
                    if (eachRole.getName().equals(role.getName())) {
                        throw new DuplicateRequestException("Ce rôle est déjà attribué à cet utilisateur");
                    }
                }
        );
        log.info("Ajout du rôle {} pour l'utilisateur {}", rolename, username);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser addProfilpicToUser(MultipartFile file) throws IOException {
        AppUser currentUser = appUserRepo.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElse(null);

        //Suppression de l'ancienne image
        assert currentUser != null;
        if (currentUser.getProfilePicture() != null) {
            profilPicRepository.delete(currentUser.getProfilePicture());
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        currentUser.setProfilePicture(
                new ProfilPic(
                        fileName,
                        file.getContentType(),
                        file.getBytes())
        );
        return appUserRepo.save(currentUser);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        AppUser currentUser = appUserRepo.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElse(null);

        Collection<SimpleGrantedAuthority> nowAuthorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities();

        assert currentUser != null;
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(currentUser.getUsername(), nowAuthorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        if (user.getUsername() != null) {
            currentUser.setUsername(user.getUsername());
        }

        if (user.getName() != null) {
            currentUser.setName(user.getName());
        }

        return appUserRepo.save(currentUser);
    }

    @Override
    public Void deleteUser() {
        appUserRepo.delete(
                appUserRepo.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur inexistant"))
        );
        return null;
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Recup de l'utilisateur {}", username);
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + username + " n'existe pas"));
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Recup de tous les utilisateurs");
        return appUserRepo.findAll();
    }

    public AppUser getCredentials() {
        return appUserRepo.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElse(null);
    }
}
