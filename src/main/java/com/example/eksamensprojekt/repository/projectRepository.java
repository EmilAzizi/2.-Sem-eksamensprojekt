package com.example.eksamensprojekt.repository;
import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.StatusOption;
import com.example.eksamensprojekt.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

@Repository
public class projectRepository {
    private User user;
    private  Project project;

    private  List<User> userList = new ArrayList<>();
    private List<StatusOption> status = new ArrayList<>();

    private String JDBC_USERNAME = System.getenv("JDBC_USERNAME");
    private String JDBC_DATABASE_URL = System.getenv("JDBC_DATABASE_URL");
    private String JDBC_PASSWORD = System.getenv("JDBC_PASSWORD");

    public projectRepository() {
        user = new User();
        project = new Project();
    }

    public List<User> getUserList() throws SQLException {
        if (userList.isEmpty()) {
            userList = getUsers();
        }
        return userList;
    }

    public List<StatusOption> getStatus() throws SQLException {
        if(status.isEmpty()){
            status = getStatuses();
        }
        return status;
    }



    public void insertUser(User newUser) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (userName, userPassword)" +
                    "VALUES(?,?);");
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getUserPassword());
            ps.executeUpdate();
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserPassword(resultSet.getString(3));
                user.setProjectID(resultSet.getInt(4));
                user.setUsersProjects(getProjectsForUser(user.getUserID()));
                userList.add(user);
            }
        }
        return userList;
    }

    public List<Project> getProjectsForUser(int userID) throws SQLException{
        List<Project> projects = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM project WHERE ownerID = ?");
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Project project = new Project();
                project.setID(resultSet.getInt("projectID"));
                project.setName(resultSet.getString("projectName"));
                project.setDescription(resultSet.getString("projectDescription"));
                project.setDate(resultSet.getString("projectDate"));
                projects.add(project);
            }
        }
        return projects;
    }

    public void createUser(User newUser) throws SQLException {
        insertUser(newUser);
        User userToBeCreated = newUser;
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
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
    }

    public Boolean authenticateUser(User userToBeComparedTo, int ID) throws SQLException {
        boolean isAuthenticated = false;
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            for (User user : userList) {
                if(user.getUserID() == ID) {
                    if (userToBeComparedTo.getUserName().equals(user.getUserName())
                            && userToBeComparedTo.getUserPassword().equals(user.getUserPassword())) {
                        isAuthenticated = true;
                    }
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
                    try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
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

    public void createStatus(StatusOption newStatus) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            // Insert the new project into the database
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO projectStatus (statusName) VALUES (?);");
            ps.setString(1, newStatus.getStatus());
            ps.executeUpdate();
            status.add(newStatus);
        }
    }

    public List<StatusOption> getStatuses() throws SQLException {
        List<StatusOption> statusList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM projectStatus");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                StatusOption status = new StatusOption();
                status.setStatusID(resultSet.getInt(1));
                status.setStatus(resultSet.getString(2));
                statusList.add(status);
            }
        }
        return statusList;
    }

    public void createProject(Project projectToBeCreated, int userID) throws SQLException {
        Project newProject = new Project();
        for (User userToFind : userList) {
            if (userToFind.getUserID() == userID) {
                try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO project (projectName, projectDescription, projectDate, ownerID) VALUES (?, ?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, projectToBeCreated.getName());
                    ps.setString(2, projectToBeCreated.getDescription());
                    ps.setString(3, projectToBeCreated.getDate());
                    ps.setInt(4, userID);
                    ps.executeUpdate();

                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int newProjectID = generatedKeys.getInt(1);
                        newProject.setID(newProjectID);

                        newProject.setName(projectToBeCreated.getName());
                        newProject.setDescription(projectToBeCreated.getDescription());
                        newProject.setDate(projectToBeCreated.getDate());

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
                        break;
                    }
                }
                break;
            }
        }

        if(projectToEdit != null) {
            projectToEdit.setName(projectToBeEdited.getName());
            projectToEdit.setDescription(projectToBeEdited.getDescription());
            projectToEdit.setDate(projectToBeEdited.getDate());

            try(Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
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
            throw new SQLException("User or project not found");
        }
    }

    public void deleteProject(int userID, int projectID) throws SQLException {
        Project projectToDelete = null;
        List<Project> userProjectsToDelete = null;

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

        if (userProjectsToDelete != null && projectToDelete != null) {
            userProjectsToDelete.remove(projectToDelete);

            try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM project WHERE projectID = ?;");
                statement.setInt(1, projectID);
                statement.executeUpdate();
            }
        }
    }

    public void deleteStatus(int statusID) throws SQLException {
        StatusOption statusToBeDeleted = null;
        for(StatusOption status : status){
            if(status.getStatusID() == statusID){
                try (Connection connection = DriverManager.getConnection(JDBC_DATABASE_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM projectStatus WHERE statusID = ?;");
                    statement.setInt(1, statusID);
                    statusToBeDeleted = status;
                }
            }
        }
        if(!statusToBeDeleted.equals(null)){
            status.remove(statusToBeDeleted);
        }
    }


}
