package com.agilescrum.agilescrum.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Sprint {
    private Long id;
    private LocalDateTime endDate;
    private Teams team;
    private Integer number;
    private String review;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "TEAM_ID")
    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
