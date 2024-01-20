package com.agilescrum.agilescrum.common;

public class TaskDto {
    private Long id;
    private String description;
    private Boolean status;
    private Long sprintId;
    private Long assignedUserId;
    private String assignedUsername;

    public TaskDto(Long id, String description, Long sprintId, Long assignedUserId, String assignedUsername) {
        this.id = id;
        this.description = description;
        this.status = Boolean.FALSE;
        this.sprintId = sprintId;
        this.assignedUserId = assignedUserId;
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

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public String getAssignedUsername() {
        return assignedUsername;
    }
}
