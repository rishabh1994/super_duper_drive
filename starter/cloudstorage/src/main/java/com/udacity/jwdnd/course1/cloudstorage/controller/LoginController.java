package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Slf4j
@AllArgsConstructor
public class LoginController {

    static int counter = 1;

    private UserService userService;
    private NoteMapper noteMapper;

    @GetMapping
    public String getLoginPage() {
        log.warn("--------GET REQUEST LOGIN PAGE--------");
        if (counter == 1) {
            tempDataCreation();
            counter++;
        }
        log.warn("--------GET REQUEST LOGIN PAGE--------");
        return "login";
    }

    //TODO DELETE IT LATER.
    public void tempDataCreation() {
        User user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setPassword("a");
        user.setUserName("a");
        int i = userService.insertUser(user);
        System.out.println("THIS IS NEW  " + i);

        Note newNote = new Note();
        newNote.setUserId(1);
        newNote.setNoteDescription("description");
        newNote.setNoteTitle("title");
        log.warn("Updated note with user id of the user who posted");
        noteMapper.insertNote(newNote);


    }
}
