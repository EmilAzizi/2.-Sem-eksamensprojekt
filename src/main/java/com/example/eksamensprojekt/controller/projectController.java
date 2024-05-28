package com.example.eksamensprojekt.controller;

import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.StatusOption;
import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.service.projectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("projectManagement")
public class projectController {
    private final projectService projectService = new projectService();

    @GetMapping("")
    public String start(Model model) throws SQLException {
        model.addAttribute("userList", projectService.getUserList());
        return "startPage";
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createNewUser(@ModelAttribute User user) throws SQLException {
        projectService.createUser(user);
        return "redirect:/projectManagement";
    }

    @GetMapping("{ID}/loginPage")
    public String loginPage(Model model, @PathVariable int ID) {
        User userToBeComparedTo = new User();
        model.addAttribute("userToBeCompared", userToBeComparedTo);
        model.addAttribute("userID", ID);
        return "loginPage";
    }

    @PostMapping("{ID}/userLogin")
    public String loginPageRedirect(@ModelAttribute User userToBeComparedTo, @PathVariable int ID, Model model) throws SQLException {
        Boolean isAuthenticated = projectService.authenticateUser(userToBeComparedTo, ID);
        List<StatusOption> statusList = projectService.getStatusListFromRepository();
        if (isAuthenticated) {
            User userToFind = projectService.findUserByIDFromRepository(ID);
            model.addAttribute("userName", userToFind.getUserName());
            model.addAttribute("userProjects", userToFind.getUsersProjects());
            model.addAttribute("userID", userToFind.getUserID());
            model.addAttribute("statusOptions", statusList);
            return "login";
        } else {
            return "redirect:/projectManagement";
        }
    }

    @GetMapping("{ID}/userPage")
    public String viewUser(Model model, @PathVariable int ID) throws SQLException {
        User userToView = projectService.findUserByIDFromRepository(ID);
        List<Project> userProjects = userToView.getUsersProjects();
        List<StatusOption> statusList = projectService.getStatusListFromRepository();
        model.addAttribute("userName", userToView.getUserName());
        model.addAttribute("userToView", userToView);
        model.addAttribute("userProjects", userProjects);
        model.addAttribute("userID", ID);
        model.addAttribute("statusOptions", statusList);
        return "login";
    }

    @GetMapping("/{ID}/deleteUser")
    public String deleteUser (Model model, @PathVariable int ID) {
        User userToComparePassword = new User();
        model.addAttribute("userToComparePassword", userToComparePassword);
        model.addAttribute("userID", ID);
        return "deleteUser";
    }

    @PostMapping("/{ID}/deleteUser")
    public String deleteUser (@ModelAttribute User userToCompare, Model model, @PathVariable int ID) throws SQLException {
        boolean isDeleted = projectService.deleteUser(userToCompare, ID);
        if (!isDeleted) {
            model.addAttribute("errorMessage", "Forkert brugernavn eller kodeord. Prøv igen");
            model.addAttribute("userToComparePassword", new User());
            return "deleteUser";
        }
        return "redirect:/projectManagement";
    }

    @GetMapping("/{ID}/userLogin/createProject")
    public String createProjectForm(@PathVariable int ID, Model model) {
        Project projectToBeCreated = new Project();
        model.addAttribute("userID", ID);
        model.addAttribute("projectToBeCreated", projectToBeCreated);
        return "createProject";
    }

    @PostMapping("/{ID}/userLogin/createProject")
    public String createProject(@ModelAttribute Project projectToBeCreated, @PathVariable int ID, Model model) throws SQLException {
        projectService.createProjectFromRepository(projectToBeCreated, ID);
        model.addAttribute("userID", ID);
        return "projectAccept";
    }

    @GetMapping("/{userID}/userPage/editProject/{projectID}")
    public String editProject(@PathVariable int userID, Model model, @PathVariable int projectID){
        Project projectToEdit = projectService.findProjectByIDFromRepository(userID, projectID);
        model.addAttribute("projectToEdit", projectToEdit);
        model.addAttribute("userID", userID);
        model.addAttribute("projectID", projectID);

        return "editProject";
    }

    @PostMapping("/{userID}/userPage/editProject/{projectID}")
    public String editProject(@ModelAttribute Project projectToBeUpdated, @PathVariable int userID, @PathVariable int projectID) throws SQLException{
        projectService.editProjectFromRepository(projectToBeUpdated, userID, projectID);
        return "redirect:/projectManagement/" + userID + "/userPage";
    }

    @PostMapping("/{userID}/userPage/deleteProject/{projectID}")
    public String deleteProject(@PathVariable int userID, @PathVariable int projectID) throws SQLException {
        projectService.deleteProjectFromRepository(userID, projectID);
        return "redirect:/projectManagement/" + userID + "/userPage";
    }

    @GetMapping("/{userID}/userPage/createStatus/{projectID}")
    public String createStatus(Model model, @PathVariable int userID, @PathVariable int projectID){
        StatusOption status = new StatusOption();
        model.addAttribute("newStatus", status);
        model.addAttribute("userID", userID);
        model.addAttribute("projectID", projectID);

        return "createStatus";
    }

    @PostMapping("/{userID}/userPage/createStatus/{projectID}")
    public String createStatus(@ModelAttribute StatusOption status, @PathVariable int userID, @PathVariable int projectID) throws SQLException {
        projectService.createStatusFromRepository(status);
        return "redirect:/projectManagement/" + userID + "/userPage";
    }

    @GetMapping("/{userID}/userPage/deleteStatus/{statusID}")
    public String deleteStatus(Model model, @PathVariable int userID, @PathVariable int statusID) throws SQLException {
        List<StatusOption> status = projectService.getStatusListFromRepository();
        model.addAttribute("projectStatuses", status);
        model.addAttribute("statusID", statusID);

        return "deleteStatus";
    }

    @PostMapping("/{userID}/userPage/deleteStatus/{statusID}")
    public String deleteStatus(@PathVariable int userID, @PathVariable int statusID) throws SQLException {
        projectService.deleteStatusFromRepository(statusID);
        return "redirect:/projectManagement/" + userID + "/userPage";
    }
}
