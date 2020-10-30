package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id="signup-link")
    private WebElement signUpLink;

    @FindBy(tagName = "h1")
    private WebElement h1Tag;

    @FindBy(id="inputFirstName")
    private WebElement signUpFirstName;

    @FindBy(id="inputLastName")
    private WebElement signUpLastName;

    @FindBy(id="inputUsername")
    private WebElement signUpUserName;

    @FindBy(id="inputPassword")
    private WebElement signUpPassword;

    @FindBy(id="submit-button")
    private WebElement submitButton;

    @FindBy(id="backToLogin")
    private WebElement goToLogin;

    @FindBy(id="error-msg")
    private WebElement errorMessage;

    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void goToSignUpPage(){
        signUpLink.click();
    }

    public String h1Text(){
        return h1Tag.getText();
    }

    public void signUp(String firstName, String lastName, String userName, String password) throws InterruptedException {
        signUpFirstName.sendKeys(firstName);
        signUpLastName.sendKeys(lastName);
        signUpUserName.sendKeys(userName);
        signUpPassword.sendKeys(password);
        submitButton.click();
        Thread.sleep(1000);
    }

    public void goToLogin(){
        goToLogin.click();
    }

    public void login(String userName, String password) throws InterruptedException {
        signUpUserName.sendKeys(userName);
        signUpPassword.sendKeys(password);
        submitButton.click();
        Thread.sleep(2000);
    }

    public Boolean getErrorMessage(){
        return errorMessage.isDisplayed();
    }
}
