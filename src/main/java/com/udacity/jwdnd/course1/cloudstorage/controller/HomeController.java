package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homePage(Authentication authentication, Model model, Note note){

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        System.out.println(authentication.getName() + " " + userId);
        List<Note> noteList = noteService.getAllNotesForUser(userId);
        model.addAttribute("allNotes", noteList);

        List<File> fileList = fileService.getAllFiles(userId);
        model.addAttribute("allFiles", fileList);

        List<Credential> credentialList = credentialService.getAllCredentials(userId);
        model.addAttribute("allCredentials", credentialList);
        return "home";
    }

    @PostMapping
    public String postNote(){

        return "home";
    }

//    @ModelAttribute("note")
//    public Note createNote(){
//        Note note = new Note();
//        return note;
//    }
//    @ModelAttribute("allNotes")
//    public List<Note> notesList(Authentication authentication){
//        String userName = authentication.getName();
//        User user = userService.getUser(userName);
//        Integer userId = user.getUserId();
//        List<Note> noteList = noteService.getAllNotesForUser(userId);
//        return noteList;
//    }
}
