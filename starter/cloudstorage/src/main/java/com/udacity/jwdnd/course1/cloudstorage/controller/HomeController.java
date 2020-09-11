package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
    private final CredentialMapper credentialMapper;
    private final FileMapper fileMapper;
    private  final EncryptionService encryptionService;

    @GetMapping()
    public String getHomePage(@ModelAttribute("newNote") Note newNote,
                              @ModelAttribute("newCredential") Credential newCredential,
                              Authentication authentication,
                              Model model) {
        model.addAttribute("encryptionService", encryptionService);
        log.debug("--------GET HOME PAGE--------");
        if (!authentication.isAuthenticated()) {
            log.debug("Unauthenticated user");
            log.debug("--------GET HOME PAGE--------");
            return "login";
        }
        String name = authentication.getName();
        log.debug("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        if (user == null) {
            log.debug("Null user");
            log.debug("--------GET HOME PAGE--------");
            return "login";
        }
        log.debug("User object found with above name : {}", user);
        List<Note> noteList = noteMapper.getAllNotesForAUser(user.getUserId());
        model.addAttribute("noteList", noteList);
        List<File> fileList = fileMapper.getAllFileForAUser(user.getUserId());
        model.addAttribute("fileList", fileList);
        List<Credential> credentialList = credentialMapper.getCredentialsForAUser(user.getUserId());
        model.addAttribute("credentialList", credentialList);
        log.debug("--------GET HOME PAGE--------");
        return "home";
    }

}
