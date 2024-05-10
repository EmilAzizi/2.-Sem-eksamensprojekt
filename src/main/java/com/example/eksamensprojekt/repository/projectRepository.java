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

    public List<User> getUserList() throws SQLException{
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "marksej123")) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ps.executeQuery();
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserPassword(resultSet.getString(3));
                user.setProjectID(resultSet.getInt(4));
                userList.add(user);
            }
            return userList;
        }

        public void createUser (User newUser) throws SQLException {
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

        public Boolean authenticateUser (User userToBeComparedTo,int ID) throws SQLException {
            boolean isAuthenticated = false;
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
                for (User user : userList) {
                    if (userToBeComparedTo.getUserName().equals(user.getUserName())
                            && userToBeComparedTo.getUserPassword().equals(user.getUserPassword())) {
                        isAuthenticated = true;
                    }
                }
            }
        }

        public boolean authenticateUser (User userToBeComparedTo) throws SQLException {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "marksej123")) {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE userName = ? AND userPassword = ?");
                ps.setString(1, userToBeComparedTo.getUserName());
                ps.setString(2, userToBeComparedTo.getUserPassword());
                ResultSet rs = ps.executeQuery();
                return rs.next(); // If a row exists, then the user is authenticated
            }
        }


        public boolean deleteUser (User userToComparePassword) throws SQLException {
            User userToBeDeleted = null;
            for (User ul : userList) {
                if (userToComparePassword.getUserPassword().equals(ul.getUserPassword())) {
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "As3146594250")) {
                        PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE userPassword = ?;");
                        ps.setString(1, userToComparePassword.getUserPassword());
                        ps.executeUpdate();
                        userToBeDeleted = ul;
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
    }
}
