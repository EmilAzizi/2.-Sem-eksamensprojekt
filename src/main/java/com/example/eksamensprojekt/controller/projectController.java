package com.example.eksamensprojekt.controller;

import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.service.projectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("projectManagement")
public class projectController {
    projectService PS = new projectService();

    @GetMapping("")
    public String start(Model model) {
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
      
    @GetMapping("/createUser")
    public String createUser(Model model){
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createNewUser(@ModelAttribute User user) throws SQLException {
        PS.createUser(user);
        return "redirect:/projectManagement";
    }
}
