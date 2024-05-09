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

    public List<User> getUserList() {
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

    public void createUser(User newUser) throws SQLException {
        insertUser(newUser);
        User userToBeCreated = newUser;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")){
            Statement statement = connection.createStatement();
            String selectSQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while(resultSet.next()){
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
            for(User user : userList){
                System.out.println(user.toString());
            }
    }

    public Boolean authenticateUser(User userToBeComparedTo, int ID) throws SQLException {
        boolean isAuthenticated = false;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")) {
            for(User user : userList) {
                if (userToBeComparedTo.getUserName().equals(user.getUserName())
                        && userToBeComparedTo.getUserPassword().equals(user.getUserPassword())) {
                    isAuthenticated = true;
                }
            }
        }
        return isAuthenticated;
    }
}
