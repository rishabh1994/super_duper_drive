package com.udacity.jwdnd.course1.cloudstorage;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@AllArgsConstructor
public class SignupPage {

    private final WebDriverWait webDriverWait;

    public void doSignUp(String firstName, String lastName, String userName, String password) {

        WebElement inputFirstName = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputFirstName")));
        WebElement inputLastName = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputLastName")));
        WebElement inputUsername = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        WebElement inputPassword = webDriverWait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        WebElement signupButton = webDriverWait.until(webDriver -> webDriver.findElement(By.id("signupButton")));

        inputFirstName.clear();
        inputLastName.clear();
        inputUsername.clear();
        inputPassword.clear();

        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);

        signupButton.click();
    }

}
