package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Credential {
    private Integer credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userId;
}
