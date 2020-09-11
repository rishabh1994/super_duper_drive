package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CredentialTests {

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
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.webDriverWait = new WebDriverWait(webDriver, 4);
        ;
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
    public void createCredential() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");

        CredentialPage credentialPage = new CredentialPage(webDriverWait);
        String url = "test";
        String userName = "SAMPLE CRED";
        String password = "SAMPLE DESCRIPTION";
        credentialPage.createCredential(url, userName, password);

        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        String userTable = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable"))).getText();
        assertTrue(userTable.contains(url));
        assertTrue(userTable.contains(userName));
        assertFalse(userTable.contains(password));
    }

    @Test
    public void deleteCredential() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");

        CredentialPage credentialPage = new CredentialPage(webDriverWait);
        String url = "test";
        String userName = "SAMPLE CRED";
        String password = "SAMPLE DESCRIPTION";
        credentialPage.createCredential(url, userName, password);

        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCredentialButton"))).click();
        Thread.sleep(2000);
        webDriver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        Thread.sleep(2000);
        String userTable = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable"))).getText();
        assertFalse(userTable.contains(url));
        assertFalse(userTable.contains(userName));
    }

    @Test
    public void editCredential() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");

        CredentialPage credentialPage = new CredentialPage(webDriverWait);
        String url = "test";
        String userName = "SAMPLE CRED";
        String password = "SAMPLE DESCRIPTION";
        credentialPage.createCredential(url, userName, password);

        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialButton"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(" UPDATED");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(" UPDATED");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialsSave"))).click();
        webDriver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();
        Thread.sleep(2000);
        String userTable = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable"))).getText();
        assertTrue(userTable.contains(url + " UPDATED"));
        assertTrue(userTable.contains(userName + " UPDATED"));
    }

}
