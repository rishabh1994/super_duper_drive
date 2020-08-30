package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;

}
