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
class NoteTests {

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
    public void createNote() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");

        NotePage notePage = new NotePage(webDriverWait);
        String noteTitle = "SAMPLE NOTE";
        String noteDescription = "SAMPLE DESCRIPTION";
        notePage.createNote(noteTitle, noteDescription);

        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        String userTable = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable"))).getText();
        assertTrue(userTable.contains(noteTitle));
        assertTrue(userTable.contains(noteDescription));
    }

    @Test
    public void deleteNote() throws Exception{
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriverWait);
        signupPage.doSignUp("a", "b", "c", "d");
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriverWait);
        loginPage.doLogin("c", "d");
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + this.port + "/home");

        NotePage notePage = new NotePage(webDriverWait);
        String noteTitle = "SAMPLE NOTE";
        String noteDescription = "SAMPLE DESCRIPTION";
        notePage.createNote(noteTitle, noteDescription);

        webDriver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(2000);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNoteButton"))).click();
        Thread.sleep(2000);
        webDriver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        Thread.sleep(2000);
        String userTable = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable"))).getText();
        assertFalse(userTable.contains(noteTitle));
        assertFalse(userTable.contains(noteDescription));


    }

}
