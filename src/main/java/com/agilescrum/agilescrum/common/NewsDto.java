package com.agilescrum.agilescrum.common;

import java.time.LocalDateTime;

public class NewsDto {
    private Long id;
    private String title;
    private String body;
    private String author;
    private LocalDateTime datePosted;
    private String image;

    public NewsDto(Long id, String title, String body, String author, LocalDateTime datePosted, String image) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.datePosted = datePosted;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    private String datePostedFormatted;

    public String getDatePostedFormatted() {
        return datePostedFormatted;
    }

    public void setDatePostedFormatted(String datePostedFormatted) {
        this.datePostedFormatted = datePostedFormatted;
    }
}
