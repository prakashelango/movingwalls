package com.base.movingwalls.model.user;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class UserInfo {

    static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "The database generated user, token and session mapping ID.")
    private Long id;

    @ApiModelProperty(notes = "user name")
    private String username;

    @ApiModelProperty(notes = "Role of User")
    private String role;

    @ApiModelProperty(notes = "User password")
    private String password;

    @ApiModelProperty(notes = "Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.")
    private boolean enabled;

    @ApiModelProperty(notes = "The database generated user, token and session mapping created time.")
    private LocalDateTime createdTime;

    @ApiModelProperty(notes = "The database generated user, token and session mapping updated time.")
    private LocalDateTime updatedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public UserInfo() {
    }

    public UserInfo(String username, String password, boolean enabled, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
