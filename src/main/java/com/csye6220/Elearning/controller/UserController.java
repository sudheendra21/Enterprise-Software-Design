package com.csye6220.Elearning.controller;


import com.csye6220.Elearning.DAO.UserDAO;
import com.csye6220.Elearning.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController
{

    //Create
    @GetMapping("/usercreate")
    public String showuser(){

        User u = new User("Sudheendra","Sabnvaisu");
        UserDAO.saveUser(u);
        User u1 = new User("Lakshmi","Kumar");
        UserDAO.saveUser(u1);
        User u2 = new User("Sudheer","Muppavarapu");
        UserDAO.saveUser(u2);









        return "user";

    }







}
