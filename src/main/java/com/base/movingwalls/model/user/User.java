package com.base.movingwalls.model.user;

import com.base.movingwalls.common.core.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_time", insertable = true, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", insertable = false, updatable = true)
    private LocalDateTime updatedTime;

    public static Builder<User> builder() {
        return Builder.of(User.class);
    }

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

}
