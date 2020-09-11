package com.udacity.jwdnd.course1.cloudstorage;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@AllArgsConstructor
public class LoginPage {

    private WebDriverWait webDriverWait;

    public void doLogin(String userName,
                        String password) {

        WebElement inputUsername = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        WebElement inputPassword = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        WebElement loginSubmitButton = webDriverWait.until(webDriver -> webDriver.findElement(By.id("loginSubmitButton")));

        inputUsername.clear();
        inputPassword.clear();

        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);

        loginSubmitButton.click();
    }

}
