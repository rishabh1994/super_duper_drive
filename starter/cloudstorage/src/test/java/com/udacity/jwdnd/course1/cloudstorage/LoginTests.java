package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class LoginTests {

    @LocalServerPort
    private int port;

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

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
    public void doSuccessfulLoginAfterSignup() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("logoutButton"))).click();
        Thread.sleep(2000);
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/login");
        Thread.sleep(2000);
        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/login");
    }

    @Test
    public void doUnsuccessfulLoginAfterSignup() {
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d2");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/login?error");
        WebElement invalidUserNameOrPassword = webDriverWait.until(webDriver -> webDriver.findElement(By.id("invalidUserNameOrPassword")));
        assertTrue(invalidUserNameOrPassword.isDisplayed());
    }

    @Test
    public void testUnauthorizedAccess() {
        webDriver.get("http://localhost:" + this.port + "/home");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/login");
    }

}
