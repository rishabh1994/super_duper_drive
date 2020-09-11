package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
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
    private HashService hashService;
    private EncryptionService encryptionService;

    @PostMapping("/home/credential/createOrUpdate")
    public String addOrUpdateCredential(@ModelAttribute("newCredential") Credential newCredential,
                                        Authentication authentication,
                                        Model model) {

        log.debug("--------POST /home/credential/createOrUpdate PAGE--------");
        log.debug("Credential object fetched from form : {}", newCredential);
        String name = authentication.getName();
        log.debug("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        log.debug("User object found with above name : {}", user);
        newCredential.setUserId(user.getUserId());
        log.debug("Updated credential with user id of the user who posted.");

        String encodedKey = hashService.getRandomString();
        newCredential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(), encodedKey);
        newCredential.setPassword(encryptedPassword);
        boolean isEditRequest = newCredential.getCredentialId() != null;
        int credentialInsertionStatus;

        if (isEditRequest) {
            credentialInsertionStatus = credentialMapper.updateCredential(newCredential);
        } else {
            credentialInsertionStatus = credentialMapper.insertCredential(newCredential);
        }
        log.debug("Credential insertion status id : {}", credentialInsertionStatus);

        if (credentialInsertionStatus < 0) {
            log.error("Error while inserting credential. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.debug("Credential inserted successfully : {}", newCredential);
            model.addAttribute("isOperationSuccess", true);
        }
        log.debug("--------POST /home/credential/createOrUpdate PAGE--------");

        return "result";
    }

    @GetMapping("/home/credential/delete")
    public String deleteCredential(@RequestParam("id") Integer credentialId, Model model) {
        log.debug("--------GET /home/credential/delete PAGE--------");
        log.debug("Received credential deletion request for id : {}", credentialId);
        int credentialDeletionStatus = credentialMapper.deleteCredential(credentialId);
        log.debug("credentialDeletionStatus : {}", credentialDeletionStatus);
        if (credentialDeletionStatus < 0) {
            log.error("Error while deleting credential. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.debug("credential deleted successfully!");
            model.addAttribute("isOperationSuccess", true);
        }
        log.debug("--------GET /home/credential/delete PAGE--------");

        return "result";
    }
}
