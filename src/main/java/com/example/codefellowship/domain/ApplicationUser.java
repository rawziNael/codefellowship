package com.example.codefellowship.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String bio;

    //lab17
    @OneToMany(mappedBy="applicationUser", cascade = CascadeType.ALL)
    public List<Post> posts;

    //lab18
    @ManyToMany
    @JoinTable(
            name = "follower_followed",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "followed_id")}
    )
    public List<ApplicationUser> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.LAZY)
    public List<ApplicationUser> followers;

    public void setFollowers(ApplicationUser user) {
        this.followers.add(user);
    }


    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    //hamzeh
    public List<ApplicationUser> getFollowing() {
        return following;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }
}
