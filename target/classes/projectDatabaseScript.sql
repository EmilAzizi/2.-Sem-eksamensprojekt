CREATE DATABASE IF NOT EXISTS projectmanagement;
USE projectmanagement;

CREATE TABLE IF NOT EXISTS label(
    labelID INT auto_increment primary key,
    labelName varchar(255)
);

CREATE TABLE IF NOT EXISTS contributer(
    contributerID INT AUTO_INCREMENT PRIMARY KEY,
    contrbuterName varchar(255),
    contributerRole varchar(255)
);

CREATE TABLE IF NOT EXISTS project (
    projectID INT AUTO_INCREMENT PRIMARY KEY,
    projectName varchar(255),
    projectDate varchar(255),
    projectDescription varchar(255),
    labelID INT,
    contributersID INT,
    FOREIGN KEY (labelID) REFERENCES label(labelID),
    FOREIGN KEY (contributersID) REFERENCES users(contributerID)
);

CREATE TABLE IF NOT EXISTS users(
    userID INT AUTO_INCREMENT PRIMARY KEY,
    userName varchar(255),
    userPassword varchar(255),
    projectID INT,
    FOREIGN KEY (projectID) REFERENCES project(projectID)
);