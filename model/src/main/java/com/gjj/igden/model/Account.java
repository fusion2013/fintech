package com.gjj.igden.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "account")
public class Account implements UserDetails {


    private Integer id;
    private String accountName;
    private String email;
    private String additionalInfo;
    private String password;
    private String creationDate;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
    private byte[] image;
    private boolean expired;
    private List<WatchListDesc> dataSets;


    public Account() {
    }

    public Account(String accountName, String email, String additionalInfo, String password,  List<WatchListDesc> dataSets, String creationDate) {
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.password = password;
        this.dataSets=dataSets;
        this.creationDate = creationDate;
    }

    public Account(int id, String accountName, String email,
                   String additionalInfo, String password, List<WatchListDesc> dataSets, String creationDate) {
        this.id = id;
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.password = password;
        this.creationDate = creationDate;
        this.dataSets = dataSets;
    }

    public Account(String accountName, String email,
                   String additionalInfo) {
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
    }

    public Account(Integer id, String accountName, String email, String additionalInfo,
                   String creationDate) {
        this.id = id;
        this.accountName = accountName;
        this.setEmail(email);
        this.additionalInfo = additionalInfo;
        this.creationDate = creationDate;
    }


    public Account(int accountId, String accountName, String email, String additionalInfo) {
        this(accountId, accountName, email, additionalInfo, (String) null);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "account_name", unique = true)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "additional_info")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "creation_date")
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Column(name = "expired", columnDefinition = "boolean default false", nullable = false)
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
    public boolean isEnabled() {
        return this.enabled;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    public List<WatchListDesc> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<WatchListDesc> dataSets) {
        this.dataSets = dataSets;
    }

    @Override
    @Transient
    public String getUsername() {
        return getAccountName();
    }


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }


    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(getEmail(), accountName);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account && ((Account) obj).getEmail().equals(this.getEmail()) &&
                Objects.equals(((Account) obj).accountName, this.accountName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" [id=");
        builder.append(id);
        builder.append(", accountName= ");
        builder.append(accountName);
        builder.append(", email= ");
        builder.append(getEmail());
        builder.append(", additionalInfo= ");
        builder.append(additionalInfo);
        builder.append(", data_containers Sets names= ");
        builder.append("]\n");
        return builder.toString();
    }
}
