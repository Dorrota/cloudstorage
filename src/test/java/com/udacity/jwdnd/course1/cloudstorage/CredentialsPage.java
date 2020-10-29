package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {

    @FindBy(id="nav-credentials-tab")
    private WebElement credTab;

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="cred-username")
    private WebElement userName;

    @FindBy(id="cred-password")
    private WebElement password;

    @FindBy(id="new-credential")
    private WebElement newCredentialButton;

    @FindBy(id="edit-credential")
    private WebElement editCredButton;

    @FindBy( id="delete-cred")
    private WebElement deleteCredButton;

    @FindBy(id="credential-url")
    private WebElement credUrl;

    @FindBy(id="credential-username")
    private WebElement credUsername;

    @FindBy(id="credential-password")
    private WebElement credPassword;

    @FindBy(id="save-changes")
    private WebElement submitCred;

    @FindBy(name="home-page")
    private WebElement goToHomePage;

    @FindBy(id="cred-url")
    private WebElement credUrlDisplay;

    @FindBy(id="cred-username")
    private WebElement credUsernameDisplay;

    @FindBy(id="cred-password")
    private WebElement credPasswordDisplay;


    public CredentialsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void logout(){
        logoutButton.click();
    }

    public void goToCredentialsTab(){
        credTab.click();
    }

    public void addNewCredential(String url, String username, String password){
        goToCredentialsTab();
        newCredentialButton.click();
        credUrl.sendKeys(url);
        credUsername.sendKeys(username);
        credPassword.sendKeys(password);
        submitCred.click();
        goToHomePage.click();
        goToCredentialsTab();
    }

    public String credUrlDisplayed(){
        return credUrlDisplay.getText();
    }

    public String credPasswordDisplayed(){
        return credPasswordDisplay.getText();
    }

    public void editCredential(String newUrl, String newUserName, String newPassword){
        editCredButton.click();
        credUrl.sendKeys(newUrl);
        credUsername.sendKeys(newUserName);
        credPassword.sendKeys(newPassword);
        submitCred.click();
        goToHomePage.click();
        goToCredentialsTab();
    }

    public void deleteCredential(){
        deleteCredButton.click();
        goToCredentialsTab();
    }
}
