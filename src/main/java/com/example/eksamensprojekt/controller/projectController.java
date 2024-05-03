package com.example.eksamensprojekt.controller;

import com.example.eksamensprojekt.service.projectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
