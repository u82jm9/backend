package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.loggers.CustomLogger;
import com.homeapp.nonsense_BE.models.note.DTOnote;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import com.homeapp.nonsense_BE.services.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("StickyNotes/")
@CrossOrigin(origins = "http://localhost:3000")
public class StickyNoteController {

    private final CustomLogger LOGGER;
    private final StickyNoteService stickyNoteService;

    @Autowired
    public StickyNoteController(CustomLogger LOGGER, StickyNoteService stickyNoteService) {
        this.LOGGER = LOGGER;
        this.stickyNoteService = stickyNoteService;
    }

    @GetMapping("GetAll")
    public ResponseEntity<List<StickyNote>> getStickyNotes() {
        LOGGER.log("info", "Getting all Sticky Notes, GetAll API");
        List<StickyNote> list = stickyNoteService.retrieveAllNotes();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddNote")
    public ResponseEntity<HttpStatus> addStickyNote(@RequestBody DTOnote note) {
        LOGGER.log("info", "Adding new Sticky Note, AddNote API");
        stickyNoteService.create(note.getNoteTitle(), note.getNoteMessage(), note.getNoteComplete());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("DeleteNote/{id}")
    public ResponseEntity<HttpStatus> deleteStickyNote(@PathVariable(value = "id") Long stickyNoteId) {
        LOGGER.log("info", "Deleting Sticky Note, Delete Note API");
        StickyNote note = stickyNoteService.retrieveById(stickyNoteId);
        stickyNoteService.deleteNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("DeleteAllNotes")
    public ResponseEntity<HttpStatus> deleteAllNotes() {
        LOGGER.log("info", "Deleting ALL Sticky Notes, Delete Note API");
        stickyNoteService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("EditNote")
    public ResponseEntity<HttpStatus> editStickyNote(@RequestBody StickyNote note) {
        LOGGER.log("info", "Editing Sticky Note, Edit Note API");
        stickyNoteService.editStickyNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}