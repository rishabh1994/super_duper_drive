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

        boolean isAuthenticated = authentication.isAuthenticated();
        log.warn("isAuthenticated : {}", isAuthenticated);
        if (isAuthenticated) {
            String name = authentication.getName();
            log.warn("Name received from authentication : {}", name);
            User user = userMapper.getUser(name);
            log.warn("User object found with above name : {}", user);
            if (user == null) {
                log.error("Invalid user! Redirecting to login");
                log.warn("--------GET HOME PAGE--------");
                return "login";
            }

            List<Note> noteList = noteMapper.getAllNotesForAUser(user.getUserId());
            model.addAttribute("noteList", noteList);
            int notesPostedByUser = noteList == null ? 0 : noteList.size();
            log.warn("Total notes of the user : {}", notesPostedByUser);
            log.warn("Printing notes of user");
            if (noteList != null) {
                for (Note note : noteList) {
                    log.warn("Note : {}", note);
                }
            }
            log.warn("Completed printing notes of the user");

        } else {
            log.error("Authentication issue. Redirecting to login");
            log.warn("--------GET HOME PAGE--------");
            return "login";
        }

        log.warn("--------GET HOME PAGE--------");
        return "home";
    }

    @PostMapping()
    public String postHomePage(@ModelAttribute("newNote") Note newNote,
                               Authentication authentication,
                               Model model) {
        log.warn("--------POST HOME PAGE--------");

        log.warn("Note object fetched from form : {}", newNote);
        boolean isAuthenticated = authentication.isAuthenticated();
        if (isAuthenticated) {
            String name = authentication.getName();
            log.warn("Name received from authentication : {}", name);
            User user = userMapper.getUser(name);
            log.warn("User object found with above name : {}", user);
            if (user == null) {
                log.error("Invalid user! Redirecting to login");
                log.warn("--------POST HOME PAGE--------");
                return "login";
            } else {
                int userId = user.getUserId();
                newNote.setUserId(userId);
                log.warn("Updated note with user id of the user who posted");
                int noteInsertionStatusId = noteMapper.insertNote(newNote);
                log.warn("Note insertion status id : {}", noteInsertionStatusId);
                if (noteInsertionStatusId < 0) {
                    log.error("Error while inserting note. Please retry!");
                } else {
                    log.warn("Note inserted successfully : {}", newNote);
                }
            }

        } else {
            log.error("Authentication issue. Redirecting to login");
            log.warn("--------POST HOME PAGE--------");
            return "login";
        }
        log.warn("--------POST HOME PAGE--------");
        return "home";
    }
}
