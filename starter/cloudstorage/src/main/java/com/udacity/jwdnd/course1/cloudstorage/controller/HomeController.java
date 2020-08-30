package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
@Slf4j
@AllArgsConstructor
public class HomeController {

    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    @GetMapping()
    public String getHomePage(@ModelAttribute("newNote") Note newNote,
                              Authentication authentication,
                              Model model) {
        log.warn("--------GET HOME PAGE--------");
        if(!authentication.isAuthenticated()){
            log.warn("Unauthenticated user");
            log.warn("--------GET HOME PAGE--------");
            return "login";
        }
        String name = authentication.getName();
        log.warn("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        if(user == null){
            log.warn("Null user");
            log.warn("--------GET HOME PAGE--------");
            return "login";
        }
        log.warn("User object found with above name : {}", user);
        List<Note> noteList = noteMapper.getAllNotesForAUser(user.getUserId());
        model.addAttribute("noteList", noteList);
        log.warn("--------GET HOME PAGE--------");
        return "home";
    }

}
