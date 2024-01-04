package com.agilescrum.agilescrum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class About {

    @Id
    Long Id;

    public About() {
        this.Id= Long.valueOf(-1);
    }

    public Long getId() {
        return Id;
    }

    @Lob
    String aboutText;

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }
}
