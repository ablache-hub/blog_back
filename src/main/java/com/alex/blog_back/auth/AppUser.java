package com.alex.blog_back.auth;

import com.alex.blog_back.model.Article;
import com.alex.blog_back.model.ProfilPic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email(message = "Vous devez entrer un email comme nom d'utilisateur")
    private String username;
    private String name;

    @JsonIgnore
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"appUser", "data", "article"})
    @ApiModelProperty(
            value = "Image unique associé à l'utilisateur (objet de type 'ProfilPic.class', relation OneToOne)",
            dataType = "ProfilPic")
    private ProfilPic profilePicture;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"auteur"})
    @ApiModelProperty(
            value = "Articles associés à l'utilisateur (objet de type 'List Article', relation OneToMany)",
            dataType = "List<Article>")
    private List<Article> articles;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER) // Recup l'utilisateur ET ses rôles
    @ApiModelProperty(
            value = "Rôle(s) associé(s) à l'utilisateur (objet de type 'Collection Role', relation ManyToMany)",
            dataType = "Collection<Role>")
    private Collection<Role> roles = new ArrayList<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return authorities;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
