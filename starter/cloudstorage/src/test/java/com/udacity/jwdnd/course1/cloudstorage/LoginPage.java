package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "loginSubmitButton")
    private WebElement loginSubmitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void doLogin(String userName,
                         String password) {

        inputUsername.clear();
        inputPassword.clear();
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);
        loginSubmitButton.click();
    }

}
