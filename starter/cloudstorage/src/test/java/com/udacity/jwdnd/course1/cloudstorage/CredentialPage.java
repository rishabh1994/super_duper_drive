package com.udacity.jwdnd.course1.cloudstorage;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@AllArgsConstructor
public class CredentialPage {

    private final WebDriverWait webDriverWait;

    public void createCredential(String url, String userName, String password) throws Exception{
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("addACredentialButton"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(url);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(userName);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(password);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialsSave"))).click();
    }
}
