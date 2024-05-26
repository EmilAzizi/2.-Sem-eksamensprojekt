package com.example.eksamensprojekt.model;

public class StatusOption {
    private String status;

    private int statusID = 0;
    public StatusOption() {
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
