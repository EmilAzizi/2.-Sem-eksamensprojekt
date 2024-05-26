package com.example.eksamensprojekt.service;

import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.StatusOption;
import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.repository.projectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class projectService {
    static projectRepository projectRepository = new projectRepository();
    public List<User> getUserList() throws SQLException{
        return projectRepository.getUserList();
    }

    public void createUser(User newUser) throws SQLException {
        projectRepository.createUser(newUser);
    }
  
    public Boolean authenticateUser(User userToBeComparedTo, int ID) throws SQLException {
        return projectRepository.authenticateUser(userToBeComparedTo, ID);
    }

    public boolean deleteUser(User userToCompare, int ID) throws SQLException {
      return projectRepository.deleteUser(userToCompare, ID);
    }

    public User findUserByIDFromRepository(int ID){
        User userToFind = projectRepository.findUserByID(ID);
        return userToFind;
    }

    public void createProjectFromRepository(Project projectToBeCreated, int userID) throws SQLException {
        projectRepository.createProject(projectToBeCreated, userID);
    }

    public Project findProjectByIDFromRepository(int userID, int projectID){
        return projectRepository.findProjectByID(userID, projectID);
    }

    public void editProjectFromRepository(Project projectToBeEdited, int userID, int projectID) throws SQLException {
        projectRepository.editProject(projectToBeEdited, userID,projectID);
    }
    public void deleteProjectFromRepository(int userID, int projectID) throws SQLException {
        projectRepository.deleteProject(userID, projectID);
    }

    public List<StatusOption> getStatusListFromRepository() throws SQLException {
        return projectRepository.getStatus();
    }

    public void createStatusFromRepository(StatusOption status) throws SQLException {
        projectRepository.createStatus(status);
    }
}
