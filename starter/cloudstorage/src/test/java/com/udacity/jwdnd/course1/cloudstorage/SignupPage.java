package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "signupSuccess")
    private WebElement signupSuccess;

    @FindBy(id = "duplicateUser")
    private WebElement duplicateUser;

    @FindBy(id = "signupButton")
    private WebElement signupButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void doSignup(String firstName,
                         String lastName,
                         String userName,
                         String password) {

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

    public String getSignupStatus() {
        return signupSuccess.getText();
    }

    public String getDuplicateUserErrorMessage() {
        return duplicateUser.getText();
    }

    public boolean isDuplicateUserErrorMessageDisplayed() {
        return duplicateUser.isDisplayed();
    }

}
