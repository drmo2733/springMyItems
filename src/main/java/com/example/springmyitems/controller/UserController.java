package com.example.springmyitems.controller;

import com.example.springmyitems.entity.User;
import com.example.springmyitems.service.MailService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/user/add")
    public String addUserPage() {
        return "saveUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user,ModelMap map) {
        List<String> errorMsgs = new ArrayList<>();
        if (user.getName() == null || user.getName().equals("")){
            errorMsgs.add("name is required");
        }
        if (user.getSurname() == null || user.getSurname().equals("")){
            errorMsgs.add("surname is required");
        }
        if (user.getEmail() == null || user.getEmail().equals("")){
            errorMsgs.add("email is required");
        }
        if (!errorMsgs.isEmpty()){
            map.addAttribute("errors", errorMsgs);
            return "saveUser";
        }

        userService.save(user);
        mailService.sendMail(user.getEmail(), "Welcome " + user.getName(), "You have successfully register " + user.getSurname());
        return "redirect:/";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(ModelMap map, @PathVariable("id") int id) {
        map.addAttribute("user", userService.findById(id));
        return "saveUser";
    }
}

