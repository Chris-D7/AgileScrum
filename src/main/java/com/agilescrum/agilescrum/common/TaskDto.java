package com.agilescrum.agilescrum.common;

public class TaskDto {
    private Long id;
    private String description;
    private Boolean status;
    private Long sprintId;
    private String assignedUserEmail;
    private String assignedUsername;

    public TaskDto(Long id, String description, Boolean status, Long sprintId, String assignedUserEmail, String assignedUsername) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.sprintId = sprintId;
        this.assignedUserEmail = assignedUserEmail;
        this.assignedUsername = assignedUsername;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public String getAssignedUserEmail() {
        return assignedUserEmail;
    }

    public String getAssignedUsername() {
        return assignedUsername;
    }
}
