package com.example.eksamensprojekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projectManagement")
public class projectController {

    @GetMapping("")
    public String start(Model model){
        return "startPage";
    }

    @GetMapping("/project")
    public String projectSelectionPage(Model model){
        return "projectSelectionPage";
    }
}
