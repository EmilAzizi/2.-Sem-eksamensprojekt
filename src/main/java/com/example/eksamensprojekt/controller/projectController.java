package com.example.eksamensprojekt.controller;

import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.service.projectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("projectManagement")
public class projectController {
    projectService PS = new projectService();

    @GetMapping("")
    public String start(Model model) throws SQLException {
        model.addAttribute("userList", PS.getUserList());
        return "startPage";
    }

    @GetMapping("/project")
    public String projectSelectionPage(Model model) {
        return "projectSelectionPage";
    }

    @GetMapping("/projectmain")
    public String projectMainPAge(Model model) {
        return "projectMainPage";
    }

    @PostMapping("/createProject")
    public String createProject() {
        // Logic to create a new project
        return "redirect:/";
    }

    @PostMapping("/selectProject")
    public String selectProject(@RequestParam("projectId") String projectId) {
        // Logic to select a project
        return "redirect:/"; // Redirect to a specific project page or dashboard
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectId") String projectId) {
        // Logic to delete a project
        return "redirect:/";
    }

    @PostMapping("/addTask")
    public String addTask() {
        // Add task logic
        return "redirect:/";
    }

    @PostMapping("/deleteTask")
    public String deleteTask() {
        // Delete task logic
        return "redirect:/";
    }

    @PostMapping("/editTask")
    public String editTask() {
        // Edit task logic
        return "redirect:/";
    }

    @PostMapping("/addColumn")
    public String addColumn() {
        // Add task logic
        return "redirect:/";
    }

    @PostMapping("/deleteColumn")
    public String deleteColumn() {
        // Delete task logic
        return "redirect:/";
    }

    @PostMapping("/editColumn")
    public String editColumn() {
        // Edit task logic
        return "redirect:/";
    }

    @PostMapping("/addLabel")
    public String addLabel() {
        // Add task logic
        return "redirect:/";
    }

    @PostMapping("/deleteLabel")
    public String deleteLabel() {
        // Delete task logic
        return "redirect:/";
    }

    @PostMapping("/editLabel")
    public String editLabel() {
        // Edit task logic
        return "redirect:/";
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createNewUser(@ModelAttribute User user) throws SQLException {
        PS.createUser(user);
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
        Boolean isAuthenticated = PS.authenticateUser(userToBeComparedTo, ID);
        if (isAuthenticated) {
            User userToFind = PS.findUserByIDFromRepository(ID);
            model.addAttribute("userName", userToFind.getUserName());
            model.addAttribute("userProjects", userToFind.getUsersProjects());
            model.addAttribute("userID", userToFind.getUserID());
            return "login";
        } else {
            return "redirect:/projectManagement";
        }
    }

    @GetMapping("{ID}/userPage")
    public String viewUser(Model model, @PathVariable int ID){
        User userToView = PS.findUserByIDFromRepository(ID);
        List<Project> userProjects = userToView.getUsersProjects();
        model.addAttribute("userName", userToView.getUserName());
        model.addAttribute("userToView", userToView);
        model.addAttribute("userProjects", userProjects);
        model.addAttribute("userID", ID);
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
        boolean isDeleted = PS.deleteUser(userToCompare, ID);
        if (!isDeleted) {
            model.addAttribute("errorMessage", "Wrong password, the user was not deleted.");
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
        PS.createProjectFromRepository(projectToBeCreated, ID);
        model.addAttribute("userID", ID);
        return "projectAccept";
    }
}
