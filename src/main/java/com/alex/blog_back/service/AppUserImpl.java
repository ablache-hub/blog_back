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

    //TODO Ajouter controles divers
    @Override
    public AppUser newUser(SubRequestTemplate subUser) {
        log.info("Enregistrement de l'utilisateur {}", subUser.getUsername());
        subUser.setPassword(passwordEncoder.encode(subUser.getPassword()));
        return appUserRepo.save(
                new AppUser(
                        subUser.getUsername(),
                        subUser.getPassword())
        );
 /*       Role role = roleRepo.findByName("ROLE_USER");
        AppUser nouveau  = appUserRepo.findByUsername(subUser.getUsername());
        nouveau.getRoles().add(role);
        return nouveau;*/
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
    public AppUser addProfilpicToUser(MultipartFile file, String username) throws IOException {
        AppUser currentUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur, utilisateur inexistant"));

        //Suppression de l'ancienne image
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
    public AppUser updateUser(String username, AppUser user) throws IllegalAccessException {
        if (!Objects.equals(
                username,
                SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw new IllegalAccessException("Mauvais utilisateur");
        }


        AppUser currentUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'utilisateur " + username + " n'existe pas"));

        Collection<SimpleGrantedAuthority> nowAuthorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, nowAuthorities);

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
