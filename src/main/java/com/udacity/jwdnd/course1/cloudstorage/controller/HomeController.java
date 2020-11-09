package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homePage(Authentication authentication, Model model, Note note){

        model.addAttribute("encryptionService", encryptionService);
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        List<Note> noteList = noteService.getAllNotesForUser(userId);
        model.addAttribute("allNotes", noteList);

        List<File> fileList = fileService.getAllFiles(userId);
        model.addAttribute("allFiles", fileList);

        List<Credential> credentialList = credentialService.getAllCredentials(userId);
        model.addAttribute("allCredentials", credentialList);

        return "home";
    }

//    @ModelAttribute
//    public void addEncryptionService(Model model){
//        model.addAttribute("encryptionService", encryptionService);
//    }


}
