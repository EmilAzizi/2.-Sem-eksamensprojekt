package com.example.eksamensprojekt.service;

import com.example.eksamensprojekt.model.Project;
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
  
    public Boolean authenticateUser(User userToBeComparedTo, int ID) throws SQLException {
        return PR.authenticateUser(userToBeComparedTo, ID);
    }

    public boolean deleteUser(User userToCompare, int ID) throws SQLException {
      return PR.deleteUser(userToCompare, ID);
    }

    public User findUserByIDFromRepository(int ID){
        User userToFind = PR.findUserByID(ID);
        return userToFind;
    }

    public void createProjectFromRepository(Project projectToBeCreated, int userID) throws SQLException {
        PR.createProject(projectToBeCreated, userID);
    }
}
