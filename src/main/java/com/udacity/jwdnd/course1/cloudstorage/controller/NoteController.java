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
    public String addOrUpdateNote(@ModelAttribute Note note, Model model, Authentication authentication){
        if (note.getNoteId() == null){
            note.setUserid(userService.getUser(authentication.getName()).getUserId());
            noteService.saveNote(note);
        } else {
            noteService.updateNote(note);
        }
        model.addAttribute("success", "success");
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model){
        noteService.deleteNote(noteId);
        model.addAttribute("delete", "delete");
        return "result";
    }

}
