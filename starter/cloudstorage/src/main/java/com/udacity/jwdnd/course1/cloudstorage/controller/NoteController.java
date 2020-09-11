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
        log.debug("--------POST HOME PAGE--------");

        log.debug("Note object fetched from form : {}", newNote);
        String name = authentication.getName();
        log.debug("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        log.debug("User object found with above name : {}", user);
        newNote.setUserId(user.getUserId());
        log.debug("Updated note with user id of the user who posted");

        boolean isEditRequest = newNote.getNoteId() != null;
        int noteInsertionStatus;

        if (isEditRequest) {
            noteInsertionStatus = noteMapper.updateNote(newNote);
        } else {
            noteInsertionStatus = noteMapper.insertNote(newNote);
        }
        log.debug("Note insertion status id : {}", noteInsertionStatus);

        if (noteInsertionStatus < 0) {
            log.error("Error while inserting note. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.debug("Note inserted successfully : {}", newNote);
            model.addAttribute("isOperationSuccess", true);
        }
        log.debug("--------POST HOME PAGE--------");

        return "result";
    }

    @GetMapping("/home/notes/delete")
    public String deleteNote(@RequestParam("id") Integer noteId, Model model) {
        log.debug("--------GET /home/notes/delete PAGE--------");
        log.debug("Received note deletion request for id : {}", noteId);
        int noteDeletionStatus = noteMapper.deleteNote(noteId);
        log.debug("noteDeletionStatus : {}", noteDeletionStatus);
        if (noteDeletionStatus < 0) {
            log.error("Error while deleting note. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.debug("Note deleted successfully!");
            model.addAttribute("isOperationSuccess", true);
        }
        log.debug("--------GET /home/notes/delete PAGE--------");

        return "result";
    }
}
