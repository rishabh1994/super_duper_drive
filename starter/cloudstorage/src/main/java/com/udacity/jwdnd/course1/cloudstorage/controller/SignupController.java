package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/signup")
@Slf4j
public class SignupController {

    UserService userService;

    @GetMapping()
    public String getSignupPage(@ModelAttribute("newUser") User newUser, Model model) {
        log.debug("--------GET REQUEST SIGNUP PAGE--------");
        log.debug("Sign up get page");
        log.debug("--------GET REQUEST SIGNUP PAGE--------");
        return "signup";
    }

    @PostMapping
    public String postSignupPage(@ModelAttribute("newUser") User newUser, Model model) {
        log.debug("--------POST REQUEST SIGNUP PAGE--------");
        log.debug("Input user details collected from sign up form {}", newUser);
        boolean isUserNameAvailable = userService.isUserNameAvailable(newUser.getUserName());
        if (isUserNameAvailable) {
            int userId = userService.insertUser(newUser);
            log.debug("Final user inserted in DB {}", newUser);
            if (userId < 0) {
                log.error("Error while inserting the user to db. User : {}.", newUser);
                model.addAttribute("isSignupFailed", true);
            } else {
                log.debug("User added to DB successfully");
                model.addAttribute("isSignupSuccess", true);
            }
        } else {
            log.debug("User Name is not unique. Already exists in DB {}", newUser.getUserName());
            model.addAttribute("isSignupSuccess", false);
            model.addAttribute("isUserNameDuplicate", true);
        }
        log.debug("--------POST REQUEST SIGNUP PAGE--------");
        return "signup";
    }
}
