package com.codecool.helpinghands.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private int userId;
    private String firstName;
    private String lastName;
    private String userNickname;
    private String userEmail;
    private String password;
    private LocalDateTime dateJoined;
    private String userImagePath;
    private boolean isModerator;
    @ManyToMany
    @JoinTable
    private Set<Slot> userSlots;
    @Transient
    private List<GrantedAuthority> authorities;

    public User(String userNickname, String userEmail, String password) {
        this.firstName = "";
        this.lastName = "";
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.password = password;
        this.dateJoined = LocalDateTime.now();
        this.userImagePath = userImagePath;
        this.isModerator = false;
        this.authorities = new ArrayList<>();
    }

    public void addSlot(Slot slot){
        this.userSlots.add(slot);
    }

    @Override
    public String getUsername() {
        return userNickname;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
