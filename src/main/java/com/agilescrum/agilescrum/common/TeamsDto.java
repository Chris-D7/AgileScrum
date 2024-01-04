package com.agilescrum.agilescrum.common;

import java.util.List;

public class TeamsDto {

    private Long id;
    private String subject;
    private UserDto master;
    private List<UserDto> members;

    public TeamsDto(Long id, String subject, UserDto master, List<UserDto> members) {
        this.id = id;
        this.subject = subject;
        this.master = master;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public UserDto getMaster() {
        return master;
    }

    public List<UserDto> getMembers() {
        return members;
    }
}
