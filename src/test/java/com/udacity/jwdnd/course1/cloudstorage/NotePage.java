package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="add-note")
    private WebElement addNewNote;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDecs;

    @FindBy(name="save-changes")
    private WebElement submitNote;

    @FindBy(name="home-page")
    private WebElement goToHomePage;

    @FindBy(id="note-desc-display")
    private WebElement descDisplay;

    @FindBy(id="note-title-display")
    private WebElement titleDisplay;

    @FindBy(id="delete-note")
    private WebElement deleteNote;

    @FindBy(id="edit-note" )
    private WebElement editNote;

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void goToNoteTab(){
        notesTab.click();
    }

    public void goToHomePage(){
        goToHomePage.click();
    }

    public void insertNote(String title, String noteDesc){
        goToNoteTab();
        addNewNote.click();
        noteTitle.sendKeys(title);
        noteDecs.sendKeys(noteDesc);
        submitNote.click();
        goToHomePage();
        goToNoteTab();
    }

    public String getNoteTitle(){
        return titleDisplay.getText();
    }

    public String getNoteDesc(){
        return descDisplay.getText();
    }

    public void deleteNote(){
        deleteNote.click();
        goToNoteTab();
    }

    public void editNote(String newTitle, String newDesc){
        editNote.click();
        noteTitle.sendKeys(newTitle);
        noteDecs.sendKeys(newDesc);
        submitNote.click();
        goToHomePage();
        goToNoteTab();
    }

    public void logout(){
        logoutButton.click();
    }
}
