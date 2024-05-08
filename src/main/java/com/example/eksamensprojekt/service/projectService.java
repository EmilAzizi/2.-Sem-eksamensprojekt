package com.example.eksamensprojekt.service;

import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.repository.projectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class projectService {
    static projectRepository PR = new projectRepository();
    public List<User> getUserList() throws SQLException{
        return PR.getUserList();
    }

    public void createUser(User newUser) throws SQLException {
        PR.createUser(newUser);
    }


    public static boolean authenticateUser(User userToBeComparedTo) throws SQLException {
        return PR.authenticateUser(userToBeComparedTo);
    }
}
