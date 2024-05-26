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
    labelID INT,
    ownerID INT
    );

-- Add the foreign key constraint to the project table
ALTER TABLE users
    ADD CONSTRAINT fk_projectID
    FOREIGN KEY (projectID) REFERENCES project(projectID);
