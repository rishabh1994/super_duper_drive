package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private int userId;
}
