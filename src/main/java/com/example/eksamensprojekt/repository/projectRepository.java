package com.example.eksamensprojekt.repository;

import com.example.eksamensprojekt.model.Category;
import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

@Repository
public class projectRepository {
    User user;
    Project project;
    Category category;

    List<User> userList = new ArrayList<>();

    public projectRepository() {
        user = new User();
        project = new Project();
        category = new Category();
    }

    public List<User> getUserList() throws SQLException {
        if (userList.isEmpty()) {
            userList = getUsers();
        }
        return userList;
    }

    public void insertUser(User newUser) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (userName, userPassword)" +
                    "VALUES(?,?);");
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getUserPassword());
            ps.executeUpdate();
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserPassword(resultSet.getString(3));
                user.setProjectID(resultSet.getInt(4));
                userList.add(user);
            }
        }
        return userList;
    }

    public void createUser(User newUser) throws SQLException {
        insertUser(newUser);
        User userToBeCreated = newUser;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
            Statement statement = connection.createStatement();
            String selectSQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int ID = resultSet.getInt("userID");
                String name = resultSet.getString("userName");
                String password = resultSet.getString("userPassword");
                int projectID = resultSet.getInt("projectID");

                userToBeCreated.setUserID(ID);
                userToBeCreated.setUserName(name);
                userToBeCreated.setUserPassword(password);
                userToBeCreated.setProjectID(projectID);
            }
        }
        userList.add(userToBeCreated);
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }

    public Boolean authenticateUser(User userToBeComparedTo, int ID) throws SQLException {
        boolean isAuthenticated = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
            for (User user : userList) {
                if (userToBeComparedTo.getUserName().equals(user.getUserName())
                        && userToBeComparedTo.getUserPassword().equals(user.getUserPassword())) {
                    isAuthenticated = true;
                }
            }
            return isAuthenticated;
        }
    }

    public boolean deleteUser(User userToCompare, int ID) throws SQLException {
        User userToBeDeleted = null;
        for (User userToBeComparedWith : userList) {
            if (userToBeComparedWith.getUserID() == ID) {
                if (userToCompare.getUserName().equals(userToBeComparedWith.getUserName()) && userToCompare.getUserPassword().equals(userToBeComparedWith.getUserPassword())) {
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
                        PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE userPassword = ? AND userName = ?;");
                        ps.setString(1, userToCompare.getUserPassword());
                        ps.setString(2, userToCompare.getUserName());
                        ps.executeUpdate();
                        userToBeDeleted = userToBeComparedWith;
                    }
                }
            }
        }
        if (userToBeDeleted != null) {
            userList.remove(userToBeDeleted);
            return true;
        } else {
            return false;
        }

    }

    public User findUserByID(int ID) {
        User userFound = null;
        for (User userToFind : userList) {
            if (userToFind.getUserID() == ID) {
                userFound = userToFind;
            }
        }
        return userFound;
    }

    public void createProject(Project projectToBeCreated, int userID) throws SQLException {
        Project newProject = new Project();
        for (User userToFind : userList) {
            if (userToFind.getUserID() == userID) {
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO project (projectName, projectDescription, projectDate)" +
                            "VALUES(?,?,?);");
                    ps.setString(1, projectToBeCreated.getName());
                    ps.setString(2, projectToBeCreated.getDescription());
                    ps.setString(3, projectToBeCreated.getDate());
                    ps.executeUpdate();

                    Statement statement = connection.createStatement();
                    String SQL = "SELECT * FROM project";
                    ResultSet resultSet = statement.executeQuery(SQL);
                    while (resultSet.next()) {
                        int ID = resultSet.getInt("projectID");
                        String name = resultSet.getString("projectName");
                        String description = resultSet.getString("projectDescription");
                        String date = resultSet.getString("projectDate");

                        newProject.setName(name);
                        newProject.setDate(date);
                        newProject.setID(ID);
                        newProject.setDescription(description);
                    }
                    user.getUsersProjects().add(newProject);
                }
            }
        }
    }

    /*
    public Project editProject(Project projectToEdit, int userID, int projectID) throws SQLException {
        projectToEdit = new Project();
        for (User userWithProjectToEdit : userList) {
            if (userWithProjectToEdit.getUserID() == userID) {

                List<Project> projectList = userWithProjectToEdit.getUsersProjects();
                  for(Project projectToBeEdited : projectList) {
                    if (projectToBeEdited.getID() == projectID) {
                       try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")) {

                           String tableName = "";
                        if (projectToEdit.getName().contains(" ")) {
                            tableName = projectToEdit.getName().replaceAll("\\s+", "");
                        } else {
                            tableName = projectToEdit.getName();
                        }

                        PreparedStatement statement = con.prepareStatement("UPDATE " + tableName +
                                " SET projectName = ?, projectDate = ?, projectDescription = ? WHERE projectID = " + projectToBeEdited.getID());
                        //Id skal vel ikke med da den bare beholder samme ID. Det er kun relevant at ændre de 3 andre.
                        statement.setString(1, projectToBeEdited.getName());
                        statement.setString(2, projectToBeEdited.getDate());
                        statement.setString(3, projectToBeEdited.getDescription());
                        statement.executeUpdate();
                    }
                  }
                }
              }
            }
          }

     */
        }
