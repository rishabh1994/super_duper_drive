package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor

public class CredentialController {

    private UserMapper userMapper;
    private CredentialMapper credentialMapper;

    @PostMapping("/home/credential/createOrUpdate")
    public String addOrUpdateCredential(@ModelAttribute("newCredential") Credential newCredential,
                                        Authentication authentication,
                                        Model model) {

        log.warn("--------POST /home/credential/createOrUpdate PAGE--------");
        log.warn("Credential object fetched from form : {}", newCredential);
        String name = authentication.getName();
        log.warn("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        log.warn("User object found with above name : {}", user);
        newCredential.setUserId(user.getUserId());
        log.warn("Updated credential with user id of the user who posted.");

        boolean isEditRequest = newCredential.getCredentialId() != null;
        int credentialInsertionStatus;

        if (isEditRequest) {
            credentialInsertionStatus = credentialMapper.updateCredential(newCredential);
        } else {
            credentialInsertionStatus = credentialMapper.insertCredential(newCredential);
        }
        log.warn("Credential insertion status id : {}", credentialInsertionStatus);

        if (credentialInsertionStatus < 0) {
            log.error("Error while inserting credential. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.warn("Credential inserted successfully : {}", newCredential);
            model.addAttribute("isOperationSuccess", true);
        }
        log.warn("--------POST /home/credential/createOrUpdate PAGE--------");

        return "result";
    }

    @GetMapping("/home/credential/delete")
    public String deleteCredential(@RequestParam("id") Integer credentialId, Model model) {
        log.warn("--------GET /home/credential/delete PAGE--------");
        log.warn("Received credential deletion request for id : {}", credentialId);
        int credentialDeletionStatus = credentialMapper.deleteCredential(credentialId);
        log.warn("credentialDeletionStatus : {}", credentialDeletionStatus);
        if (credentialDeletionStatus < 0) {
            log.error("Error while deleting credential. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.warn("credential deleted successfully!");
            model.addAttribute("isOperationSuccess", true);
        }
        log.warn("--------GET /home/credential/delete PAGE--------");

        return "result";
    }
}
