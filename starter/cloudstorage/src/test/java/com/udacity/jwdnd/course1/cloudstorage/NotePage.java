package com.udacity.jwdnd.course1.cloudstorage;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@AllArgsConstructor
public class NotePage {

    private final WebDriverWait webDriverWait;

    public void createNote(String noteTitle, String noteDescription) throws Exception{
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("addANoteButton"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("notesClick"))).click();
    }
}
