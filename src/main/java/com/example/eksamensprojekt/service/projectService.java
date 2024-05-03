package com.example.eksamensprojekt.service;

import com.example.eksamensprojekt.model.User;
import com.example.eksamensprojekt.repository.projectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class projectService {
    projectRepository PR = new projectRepository();
    public List<User> getUserList(){
        return PR.getUserList();
    }
}
