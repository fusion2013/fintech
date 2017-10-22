package com.gjj.igden.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class AppUser implements UserDetails {

    private Long id;

    private String username;

    private String password;

    //TODO Refactor When it's known what the security requirement id
    private boolean expired;

    //TODO Refactor When it's known what the security requirement id
    private boolean enabled;

    private Set<Role> roles = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @Column(name = "account_name", unique = true)
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return !isExpired();
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return !isExpired();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
