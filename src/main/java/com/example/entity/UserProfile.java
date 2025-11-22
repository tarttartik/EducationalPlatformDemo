package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String bio;
    private String avatarUrl;

    public UserProfile() {
    }

    private UserProfile(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.bio = builder.bio;
        this.avatarUrl = builder.avatarUrl;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }

    public static class Builder {
        private Long id;
        private User user;
        private String bio;
        private String avatarUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UserProfile build() {
            if (user == null) {
                throw new IllegalStateException("User cannot be null");
            }
            return new UserProfile(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
