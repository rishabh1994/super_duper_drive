package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {
    private int credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private int userId;
}
