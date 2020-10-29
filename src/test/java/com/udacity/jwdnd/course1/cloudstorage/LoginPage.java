package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement loginUserName;
    @FindBy(id="inputPassword")
    private WebElement loginPassword;
    @FindBy(id="submit-button")
    private WebElement submit;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void login(String userName, String password) throws InterruptedException {
        loginUserName.sendKeys(userName);
        loginPassword.sendKeys(password);
        Thread.sleep(1000);
        submit.click();
    }
}
