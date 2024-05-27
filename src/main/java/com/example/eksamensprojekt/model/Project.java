package com.example.eksamensprojekt.model;

public class Project {
    int ID = 0;
    String name;
    String description;
    String date;
    String projectRole;

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
    public String getProjectRole(){
        return projectRole;
    }
    public void setProjectRole(String role){
        this.projectRole = role;
    }
}
