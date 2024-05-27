CREATE DATABASE IF NOT EXISTS projectmanagement;
USE projectmanagement;

CREATE TABLE IF NOT EXISTS users(
    userID INT AUTO_INCREMENT PRIMARY KEY,
    userName varchar(255),
    userPassword varchar(255),
    projectID INT
    );

CREATE TABLE IF NOT EXISTS project (
    projectID INT AUTO_INCREMENT PRIMARY KEY,
    projectName varchar(255),
    projectDate varchar(255),
    projectDescription varchar(255),
    ownerID INT
    );

CREATE TABLE IF NOT EXISTS projectStatus(
    statusID INT AUTO_INCREMENT PRIMARY KEY,
    statusName varchar(255)
    );

ALTER TABLE users
    ADD CONSTRAINT fk_projectID
    FOREIGN KEY (projectID) REFERENCES project(projectID);
