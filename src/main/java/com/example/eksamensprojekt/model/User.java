package com.example.eksamensprojekt.model;

import java.util.*;

public class User {
     int userID = 0;
     String userName;
     String userPassword;
     List<Project> usersProjects;

     public User(){
         usersProjects = new ArrayList<>();
     }
    private int projectID = 0;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public List<Project> getUsersProjects() {
        return usersProjects;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", projectID=" + projectID +
                '}';
    }
}
