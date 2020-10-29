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
public class CredentialsTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private CredentialsPage credentialsPage;
    private String baseUrl;
    private String credUrl = "lala.com";
    private String credUsername = "Dorro";
    private String credPassword = "t123T";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

//    @AfterAll
//    public static void afterAll() {
//        driver.quit();
//    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/signup");
    }

    @AfterEach
    public void afterEach() {
        credentialsPage.logout();
        driver.quit();
    }


        @Test
    public void submitNewCredentialTest() throws InterruptedException {
        String userName = "userName";
        String password ="test123";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        credentialsPage = new CredentialsPage(driver);
        credentialsPage.addNewCredential(credUrl, credUsername, credPassword);
        assertEquals(credUrl, credentialsPage.credUrlDisplayed());
        assertEquals(credPassword, credentialsPage.credPasswordDisplayed());
    }

    @Test
    public void editCredentialTest() throws InterruptedException {
        String userName = "userName1";
        String password ="test1231";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek1", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        credentialsPage = new CredentialsPage(driver);
        credentialsPage.addNewCredential(credUrl, credUsername, credPassword);
        credentialsPage.editCredential("NewUrl", "New username", "New*password");
        assertEquals(credUrl + "NewUrl", credentialsPage.credUrlDisplayed());
        assertEquals(credPassword + "New*password", credentialsPage.credPasswordDisplayed());
    }

    @Test
    public void deleteCredentialTest() throws InterruptedException {
        String userName = "userName2";
        String password ="test1232";
        signUpPage = new SignUpPage(driver);
        signUpPage.signUp("Dorota", "Kocurek2", userName, password);
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(userName, password);
        credentialsPage = new CredentialsPage(driver);
        credentialsPage.addNewCredential(credUrl, credUsername, credPassword);
        credentialsPage.deleteCredential();
        assertThrows(NoSuchElementException.class, ()->credentialsPage.credUrlDisplayed());
    }
}
