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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (userName, userPassword)" +
                    "VALUES(?,?);");
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getUserPassword());
            ps.executeUpdate();
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
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
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
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
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
                    // Insert the new project into the database
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO project (projectName, projectDescription, projectDate, ownerID) VALUES (?, ?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, projectToBeCreated.getName());
                    ps.setString(2, projectToBeCreated.getDescription());
                    ps.setString(3, projectToBeCreated.getDate());
                    ps.setInt(4, userID);  // Set the contributersID to the userID
                    ps.executeUpdate();

                    // Get the generated project ID
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int newProjectID = generatedKeys.getInt(1);
                        newProject.setID(newProjectID);

                        // Set project details
                        newProject.setName(projectToBeCreated.getName());
                        newProject.setDescription(projectToBeCreated.getDescription());
                        newProject.setDate(projectToBeCreated.getDate());

                        // Add the project to the user's project list
                        userToFind.getUsersProjects().add(newProject);
                    }
                }
            }
        }
    }

    public Project findProjectByID(int userID ,int projectID){
        Project projectToFind = null;

        for(User user : userList){
            if(user.getUserID() == userID){
                for(Project project : user.getUsersProjects()){
                    if(project.getID() == projectID){
                        projectToFind = project;
                    }
                }
            }
        }

        if(!projectToFind.equals(null)){
            return projectToFind;
        } else {
            return null;
        }
    }

    public void editProject(Project projectToBeEdited, int userID, int projectID) throws SQLException {
        Project projectToEdit = null;
        for(User user : userList) {
            if(user.getUserID() == userID) {
                for(Project projectToFind : user.getUsersProjects()) {
                    if(projectToFind.getID() == projectID) {
                        projectToEdit = projectToFind;
                        break; // Found the project, no need to continue loop
                    }
                }
                break; // Found the user, no need to continue loop
            }
        }

        if(projectToEdit != null) {
            // Update project details
            projectToEdit.setName(projectToBeEdited.getName());
            projectToEdit.setDescription(projectToBeEdited.getDescription());
            projectToEdit.setDate(projectToBeEdited.getDate());

            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE project SET projectName = ?, projectDescription = ?, projectDate = ? WHERE projectID = ?"
                );
                statement.setString(1, projectToEdit.getName());
                statement.setString(2, projectToEdit.getDescription());
                statement.setString(3, projectToEdit.getDate());
                statement.setInt(4, projectID);
                statement.executeUpdate();
            }
        } else {
            // Handle the case where the project or user is not found
            throw new SQLException("User or project not found");
        }
    }

    public void deleteProject(int userID, int projectID) throws SQLException {
        Project projectToDelete = null;
        List<Project> userProjectsToDelete = null;

        // Find the user and the project to delete
        for (User user : userList) {
            if (user.getUserID() == userID) {
                userProjectsToDelete = user.getUsersProjects();
                for (Project projectToFind : userProjectsToDelete) {
                    if (projectToFind.getID() == projectID) {
                        projectToDelete = projectToFind;
                        break;
                    }
                }
                break;
            }
        }

        // Check if the project and user's project list are not null
        if (userProjectsToDelete != null && projectToDelete != null) {
            userProjectsToDelete.remove(projectToDelete);

            // Establish the connection and execute the DELETE statement
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM project WHERE projectID = ?;");
                statement.setInt(1, projectID);
                statement.executeUpdate();
            }
        }
    }


}
