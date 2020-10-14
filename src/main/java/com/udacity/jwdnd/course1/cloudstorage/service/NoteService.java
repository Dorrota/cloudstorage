package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getOneNote(Integer noteId) {
        return noteMapper.findOneNote(noteId);
    }

    public List<Note> getAllNotesForUser(Integer userId){
        return noteMapper.getNotes(userId);
    }

    public void saveNote(Note note){
        noteMapper.insertNote(note);
    }

    public void deleteNote(Integer id){
        noteMapper.deleteNote(id);
    }

    public void updateNote(Note note){
        noteMapper.updateNote(note);
    }
}
