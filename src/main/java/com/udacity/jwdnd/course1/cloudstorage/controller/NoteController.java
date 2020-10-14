package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrUpdateNote(@ModelAttribute Note note, Authentication authentication){
        if (note.getNoteId() == null){
            note.setUserid(userService.getUser(authentication.getName()).getUserId());
            noteService.saveNote(note);
            System.out.println(note.getNoteId() + " " + note.getNotetitle() + " " + note.getNotedescription());
        } else {

            noteService.updateNote(note);
        }

        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId){
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }

//    @GetMapping("/edit/{noteId}")
//    public String editNote(@PathVariable Integer noteId) {
//        Note note = noteService.getOneNote(noteId);
//        noteService.
//    }
}
