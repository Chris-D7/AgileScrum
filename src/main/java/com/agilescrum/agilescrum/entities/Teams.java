package com.agilescrum.agilescrum.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Teams {

    private Long id;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String subject;

    private User master;

    private Collection<User> members;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @ManyToOne
    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    @OneToMany(mappedBy = "team")
    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }
}
