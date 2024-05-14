package com.example.eksamensprojekt.model;

public class Project {
    int ID = 0;
    String name;
    String description;
    String date;

    public Project(){
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
