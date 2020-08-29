package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class UserService {

    private HashService hashService;
    private UserMapper userMapper;

    public boolean isUserNameAvailable(String userName) {
        log.warn("Received Request to check user name uniqueness for:{}", userName);
        User user = userMapper.getUser(userName);
        log.warn("User Found from db with the above user Name : {}", user);
        boolean isUserNameAvailable = user == null;
        log.warn("isUserNameAvailable : {}", isUserNameAvailable);
        return isUserNameAvailable;
    }

    public int insertUser(User user) {
        String encodedSalt = hashService.getRandomString();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setPassword(hashedPassword);
        user.setSalt(encodedSalt);
        log.warn("Added salt and hashes password to the user details");
        int userId = userMapper.insertUser(user);
        log.warn("User Id Insert Return call : {}", userId);
        return userId;
    }
}
