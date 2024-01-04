package com.agilescrum.agilescrum.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserGroup {
    private Long id;

    @Id
    @GeneratedValue
    public Long getId(){ return id; }

    public void setId(Long id) {
        this.id = id;
    }

    private String email;
    private String userGroup;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
