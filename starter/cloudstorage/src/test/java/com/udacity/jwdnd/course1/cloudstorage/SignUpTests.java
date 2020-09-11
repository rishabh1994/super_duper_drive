package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SignUpTests {

    @LocalServerPort
    private int port;

    private WebDriverWait webDriverWait;
    private WebDriver webDriver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.webDriver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(webDriver, 1000);
    }

    @AfterEach
    public void afterEach() {
        if (this.webDriver != null) {
            webDriver.quit();
        }
        webDriver = null;
        webDriverWait = null;
    }

    @Test
    public void duplicateSignup() {

        SignupPage signupPage = new SignupPage(webDriverWait);

        webDriver.get("http://localhost:" + this.port + "/signup");
        signupPage.doSignUp("a", "b", "c", "d");

        webDriver.get("http://localhost:" + this.port + "/signup");
        signupPage.doSignUp("a", "b", "c", "d");

        WebElement duplicateUser = webDriverWait.until(webDriver -> webDriver.findElement(By.id("duplicateUser")));

        assertEquals(duplicateUser.getText(), "Duplicate user name. Please try a new user name.");
        assertTrue(duplicateUser.isDisplayed());
    }

    @Test
    public void doSignup() {

        SignupPage signupPage = new SignupPage(webDriverWait);

        webDriver.get("http://localhost:" + this.port + "/signup");
        signupPage.doSignUp("e", "f", "g", "h");

        WebElement signupSuccess = webDriverWait.until(webDriver -> webDriver.findElement(By.id("signupSuccess")));
        assertEquals(signupSuccess.getText(), "You successfully signed up! Please continue to the login page.");
    }

}
