package com.sulay.tweeter.entity;



import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "username")
    @NotBlank
    private String username;

    @Column(name= "password")
    @NotBlank
    private String password;

    @Column(name="email")
    @Email
    private String email;

    @Column(name="profile_image_url")
    @NotBlank
    private String profileImageUrl;

    @Lob
    @Column(name="biography")
    @NotBlank
    private String biography;

    @Column(name="creation_date")
    private Instant creationDate;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> followers;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> following;

    public void addFollower(User follower) {
        this.followers.add(follower);
    }

    public void removeFollower(User follower) {
        this.followers.remove(follower);
    }

    public void addFollowing(User following) {
        this.following.add(following);
    }

    public void removeFollowing(User following) {
        this.following.remove(following);
    }

}
