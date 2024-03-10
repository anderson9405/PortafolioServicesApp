package com.example.citasuamdos.model;

import java.text.Normalizer;

public class Service {

    String title, name, phone, description, lowerTitle;
    public Service(){};

    public Service(String title, String name, String phone, String description, String lowerTitle) {
        this.title = title;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.lowerTitle = lowerTitle;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
