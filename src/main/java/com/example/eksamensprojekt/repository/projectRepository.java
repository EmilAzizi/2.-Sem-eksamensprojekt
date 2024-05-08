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

    public void createUser(User newUser) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "marksej123")) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (userName, userPassword)" +
                    "VALUES(?,?);");
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getUserPassword());
            ps.executeUpdate();
            userList.add(newUser);
        }
    }



    public List<User> getUsers() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "marksej123")) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ps.executeQuery();
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserName(resultSet.getString(2));
                userList.add(user);
            }
            return userList;
        }
    }

    public boolean authenticateUser(User userToBeComparedTo) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "marksej123")) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE userName = ? AND userPassword = ?");
            ps.setString(1, userToBeComparedTo.getUserName());
            ps.setString(2, userToBeComparedTo.getUserPassword());
            ResultSet rs = ps.executeQuery();
            return rs.next(); // If a row exists, then the user is authenticated
        }
    }
}
