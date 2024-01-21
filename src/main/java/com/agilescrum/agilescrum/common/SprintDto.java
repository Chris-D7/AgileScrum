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
    private Integer totalTasks;
    private Integer doneTasks;

    public SprintDto(Long id, LocalDateTime endDate, Long team, Integer number, String review, List<TaskDto> tasks) {
        this.id = id;
        this.endDate = endDate;
        this.team = team;
        this.number = number;
        this.review = review;
        this.tasks = tasks;
        this.totalTasks = 0;
        this.doneTasks = 0;
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

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(Integer doneTasks) {
        this.doneTasks = doneTasks;
    }

    public void addDoneTasks(){
        doneTasks = doneTasks+1;
    }
}
