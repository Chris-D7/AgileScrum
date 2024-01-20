package com.agilescrum.agilescrum.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class User {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private String username;

    private String email;

    private String password;

    private Collection<Teams> teams;

    private Collection<News> news;

    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    public Collection<Teams> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Teams> teams) {
        this.teams = teams;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    public Collection<News> getNews() {
        return news;
    }

    public void setNews(Collection<News> news) {
        this.news = news;
    }
}
