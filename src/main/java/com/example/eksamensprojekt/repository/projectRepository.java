package com.example.eksamensprojekt.repository;
import com.example.eksamensprojekt.model.Category;
import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;

@Repository
public class projectRepository {
    User user;
    Project project;
    Category category;

    List<User> userList = new ArrayList<>();

    public projectRepository(){
        user = new User();
        project = new Project();
        category = new Category();
    }

    public List<User> getUserList(){
        return userList;
    }

    public void createUser(User newUser) throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanagement", "root", "Emperiusvalor1!")){
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (userName, userPassword)" +
            "VALUES(?,?);");
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getUserPassword());
            ps.executeUpdate();
            userList.add(newUser);
        }
    }
}
