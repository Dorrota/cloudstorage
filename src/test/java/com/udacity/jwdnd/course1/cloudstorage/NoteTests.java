package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private NotePage notePage;
    private String baseUrl;
    private String noteTitle = "Title";
    private String noteDesc = "Description...";

    @BeforeAll
    public static void beforeAll(){
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/signup");
    }

    @AfterEach
    public void afterEach(){
        notePage.logout();
        driver.quit();
    }

    @Test
    public void submitNoteTest() throws InterruptedException {
        String userName = "userName";
        String password ="test123";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        notePage = new NotePage(driver);
        notePage.insertNote(noteTitle, noteDesc);
        notePage.getNoteTitle();
        assertEquals(noteTitle, notePage.getNoteTitle());
    }

    @Test
    public void deleteNoteTest() throws InterruptedException {
        String userName = "userName1";
        String password ="test1231";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek1", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        notePage = new NotePage(driver);
        notePage.insertNote(noteTitle, noteDesc);
        notePage.deleteNote();
        assertThrows(NoSuchElementException.class, ()-> notePage.getNoteTitle());
    }


    @Test
    public void editNoteTest() throws InterruptedException {
        String userName = "userName2";
        String password ="test1232";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek2", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        notePage = new NotePage(driver);
        notePage.insertNote(noteTitle, noteDesc);
        notePage.editNote("New title", "New description!");
        assertEquals(noteTitle +"New title", notePage.getNoteTitle());
        assertEquals(noteDesc + "New description!", notePage.getNoteDesc());
    }
}
