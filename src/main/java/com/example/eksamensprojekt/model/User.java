package com.example.eksamensprojekt.model;

public class User {
    private int userID = 0;
    private String userName;
    private String userPassword;
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
}
