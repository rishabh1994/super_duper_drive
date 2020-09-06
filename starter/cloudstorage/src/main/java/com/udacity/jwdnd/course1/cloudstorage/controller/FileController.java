package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class FileController {

    private UserMapper userMapper;
    private FileMapper fileMapper;

    @PostMapping("/home/files/createOrUpdate")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication authentication,
                             Model model) {

        log.warn("Name of file object fetched from UI : {}", fileUpload.getOriginalFilename());
        String name = authentication.getName();
        log.warn("Name received from authentication : {}", name);
        User user = userMapper.getUser(name);
        log.warn("User object found with above name : {}", user);

        try {
            File file = new File(null, fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(), String.valueOf(fileUpload.getSize()),
                    user.getUserId(), fileUpload.getBytes());

            String currentFileName = fileUpload.getOriginalFilename();
            List<File> allFileForAUser = fileMapper.getAllFileForAUser(user.getUserId());
            for (File file1 : allFileForAUser) {
                if (file1.getFileName().equals(currentFileName)) {
                    model.addAttribute("isOperationSuccess", false);
                    return "result";
                }
            }

            int fileInsertionStatus = fileMapper.insertFile(file);
            if (fileInsertionStatus < 0) {
                log.error("Error while inserting file. Please retry!");
                model.addAttribute("isOperationSuccess", false);
            } else {
                log.warn("File inserted successfully : {}", file.getFileId());
                model.addAttribute("isOperationSuccess", true);
            }
        } catch (IOException e) {
            log.error("IOException while file processing", e);
            model.addAttribute("isOperationSuccess", false);
        }
        return "result";
    }

    @GetMapping("/home/files/delete")
    public String deleteNote(@RequestParam("id") Integer fileId, Model model) {
        log.warn("--------GET /home/files/delete PAGE--------");
        log.warn("Received file deletion request for id : {}", fileId);
        int fileDeletionStatus = fileMapper.deleteFile(fileId);
        log.warn("fileDeletionStatus : {}", fileDeletionStatus);
        if (fileDeletionStatus < 0) {
            log.error("Error while deleting note. Please retry!");
            model.addAttribute("isOperationSuccess", false);
        } else {
            log.warn("Note deleted successfully!");
            model.addAttribute("isOperationSuccess", true);
        }
        log.warn("--------GET /home/files/delete PAGE--------");

        return "result";
    }
}
