package com.example.eksamensprojekt.repository;
import com.example.eksamensprojekt.model.Category;
import com.example.eksamensprojekt.model.Project;
import com.example.eksamensprojekt.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.*;

@Repository
public class projectRepository {
    User user;
    Project project;
    Category category;

    List<User> userList = new ArrayList<>();

    public projectRepository(){
        user = new User();
        project = new Project();
        category = new Category();
    }

    public List<User> getUserList(){
        return userList;
    }

}
