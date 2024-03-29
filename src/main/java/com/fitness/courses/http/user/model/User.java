package com.fitness.courses.http.user.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "logo_id", nullable = true)
    private AttachmentEntity logo;

    @Column(nullable = true)
    private String biography;

    @Column(nullable = true)
    private String about;

    @Column(nullable = true)
    private String lastVerificationEmailCode;

    private boolean confirmed;

    @Column(nullable = false)
    private String password;

    private Set<Role> roles;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public AttachmentEntity getLogo()
    {
        return logo;
    }

    public void setLogo(AttachmentEntity logo)
    {
        this.logo = logo;
    }

    public String getBiography()
    {
        return biography;
    }

    public void setBiography(String biography)
    {
        this.biography = biography;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.roles;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public String getLastVerificationEmailCode()
    {
        return lastVerificationEmailCode;
    }

    public void setLastVerificationEmailCode(String lastVerificationEmailCode)
    {
        this.lastVerificationEmailCode = lastVerificationEmailCode;
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public User setConfirmed(boolean confirmed)
    {
        this.confirmed = confirmed;
        return this;
    }

    public String getFullName()
    {
        return String.format("%s %s", this.lastName, this.firstName);
    }
}
