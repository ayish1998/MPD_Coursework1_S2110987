package com.example.coursework1;

public class RssFeedModel {
    private String title;
    private String link;
    private String description;

    // Constructor
    public RssFeedModel(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for link
    public String getLink() {
        return link;
    }

    // Setter for link
    public void setLink(String link) {
        this.link = link;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }
}

