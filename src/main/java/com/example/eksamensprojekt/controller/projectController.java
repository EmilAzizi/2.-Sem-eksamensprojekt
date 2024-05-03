package com.example.eksamensprojekt.controller;

import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.service.projectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("projectManagement")
public class projectController {
    projectService PS = new projectService();

    @GetMapping("")
    public String start(Model model){
        model.addAttribute("userList", PS.getUserList());
        return "startPage";
    }

    @GetMapping("/project")
    public String projectSelectionPage(Model model){
        return "projectSelectionPage";
    }

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
