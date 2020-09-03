package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
public class NoteController {

    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    @PostMapping("/home/notes/createOrUpdate")
    public String postHomePage(@ModelAttribute("newNote") Note newNote,
                               Authentication authentication,
                               Model model) {
        log.warn("--------POST HOME PAGE--------");

        log.warn("Note object fetched from form : {}", newNote);
        String name = authentication.getName();
        log.warn("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        log.warn("User object found with above name : {}", user);
        newNote.setUserId(user.getUserId());
        log.warn("Updated note with user id of the user who posted");

        boolean isEditRequest = newNote.getNoteId() != null;
        int noteInsertionStatus;

        if (isEditRequest) {
            noteInsertionStatus = noteMapper.updateNote(newNote);
        } else {
            noteInsertionStatus = noteMapper.insertNote(newNote);
        }
        log.warn("Note insertion status id : {}", noteInsertionStatus);

        if (noteInsertionStatus < 0) {
            log.error("Error while inserting note. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.warn("Note inserted successfully : {}", newNote);
            model.addAttribute("isOperationSuccess", true);
        }
        log.warn("--------POST HOME PAGE--------");

        return "result";
    }

    @GetMapping("/home/notes/delete")
    public String deleteNote(@RequestParam("id") Integer noteId, Model model) {
        log.warn("--------GET /home/notes/delete PAGE--------");
        log.warn("Received note deletion request for id : {}", noteId);
        int noteDeletionStatus = noteMapper.deleteNote(noteId);
        log.warn("noteDeletionStatus : {}", noteDeletionStatus);
        if (noteDeletionStatus < 0) {
            log.error("Error while deleting note. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.warn("Note deleted successfully!");
            model.addAttribute("isOperationSuccess", true);
        }
        log.warn("--------GET /home/notes/delete PAGE--------");

        return "result";
    }
}
