package com.agilescrum.agilescrum.common;

import com.agilescrum.agilescrum.entities.Teams;

import java.time.LocalDateTime;
import java.util.List;

public class SprintDto {

    private Long id;
    private LocalDateTime endDate;
    private Long team;
    private Integer number;
    private String review;
    private List<TaskDto> tasks;

    public SprintDto(Long id, LocalDateTime endDate, Long team, Integer number, String review, List<TaskDto> tasks) {
        this.id = id;
        this.endDate = endDate;
        this.team = team;
        this.number = number;
        this.review = review;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getTeam() {
        return team;
    }

    public Integer getNumber() {
        return number;
    }

    public String getReview(){return review;}

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
