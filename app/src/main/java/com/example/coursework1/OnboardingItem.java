//
// Name:__Ayishatu Saeed__
// Student ID:__S2110987__
// Programme of Study:__Mobile Platform Development__
//
package com.example.coursework1;

public class OnboardingItem {
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String title;
    private String description;

    public OnboardingItem(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    // Getters and setters
}
