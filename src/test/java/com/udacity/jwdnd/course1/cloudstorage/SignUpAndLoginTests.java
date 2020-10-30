package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpAndLoginTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private NotePage notePage;

    private String userName = "userName";
    private String password ="test123";

    @BeforeAll
    public static void beforeAll(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterAll
    public static void afterAll(){
        driver.quit();
    }

    @BeforeEach
    public void beforeEach(){
        driver.get("http://localhost:" + port);
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
    }


    // Test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
    @Test
    public void signUpLoginAndLogoutTest() throws InterruptedException {
        signUpPage.goToSignUpPage();
        signUpPage.signUp("Dorota", "Kocurek", userName, password);
        signUpPage.goToLogin();
        assertEquals("Login", driver.getTitle());
        System.out.println(driver.getTitle());
        Thread.sleep(1000);
        loginPage.login(userName, password);
        assertEquals("Home", driver.getTitle());
        notePage = new NotePage(driver);
        notePage.logout();
        assertEquals("http://localhost:" + port +"/login", driver.getCurrentUrl());
        driver.get("http://localhost:" + port + "/home");
        Assertions.assertNotEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());
    }

    // Test verifies that an unauthorized user can only access the login and signup pages.
    @Test
    public void unauthorizedAccessToHomePageTest(){
        String baseUrl = "http://localhost:" + port;
        String h1Text = "Sign Up";
        driver.get(baseUrl + "/home");
        Assertions.assertNotEquals((baseUrl + "/home"), driver.getCurrentUrl());
        driver.get(baseUrl + "/note");
        Assertions.assertNotEquals((baseUrl + "/note"), driver.getCurrentUrl());
        assertEquals((baseUrl + "/login"), driver.getCurrentUrl());
        signUpPage.goToSignUpPage();
        assertEquals(h1Text, signUpPage.h1Text());
    }

    @Test
    public void signUpTwiceErrorTest() throws InterruptedException {
        signUpPage.goToSignUpPage();
        signUpPage.signUp("Dorota", "Kocurek", userName, password);
        signUpPage.signUp("Dorota", "Kocurek", userName, password);
        assertTrue(signUpPage.getErrorMessage());
    }
}
