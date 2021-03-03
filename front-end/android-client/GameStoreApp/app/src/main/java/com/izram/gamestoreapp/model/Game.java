package com.izram.gamestoreapp.model;

import java.io.Serializable;

public class Game implements Serializable {

    private String id;
    private String name;
    private String year;
    private String description;
    private String image_Url;

    public Game() { }

    public Game(String image_Url, String name, String year) {
        this.image_Url = image_Url;
        this.name = name;
        this.year = year;
    }

    public Game(String id, String name, String year, String description, String image_Url) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.image_Url = image_Url;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
