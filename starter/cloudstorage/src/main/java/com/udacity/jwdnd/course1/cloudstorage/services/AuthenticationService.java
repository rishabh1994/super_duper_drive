package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final UserMapper userMapper;
    private final HashService hashService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.debug("User Name received for login : {}", userName);

        User user = userMapper.getUser(userName);
        log.debug("User Found from DB for above user name is : {}", user);
        if (user != null) {
            String currentHashesValue = hashService.getHashedValue(password, user.getSalt());
            String hashValueInDb = user.getPassword();
            if (currentHashesValue.equals(hashValueInDb)) {
                log.debug("User name and passwords match! Login successful");
                return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
            } else {
                log.error("Incorrect details. Please try again with valid details");
                return null;
            }
        } else {
            log.error("No user found with this username. Please enter valid user name");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
