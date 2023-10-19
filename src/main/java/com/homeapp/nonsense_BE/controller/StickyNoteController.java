package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.note.DTOnote;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import com.homeapp.nonsense_BE.services.StickyNoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("StickyNotes/")
@CrossOrigin(origins = "http://localhost:3000")
public class StickyNoteController {

    private static Logger LOGGER = LogManager.getLogger(StickyNoteController.class);

    @Autowired
    private StickyNoteService stickyNoteService;

    @GetMapping("GetAll")
    public ResponseEntity<List<StickyNote>> getStickyNotes() {
        LOGGER.info("Getting all Sticky Notes, GetAll API");
        List<StickyNote> list = stickyNoteService.retrieveAllNotes();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddNote")
    public ResponseEntity<List> addStickyNote(@RequestBody DTOnote note) {
        LOGGER.info("Adding new Sticky Note, AddNote API");
        stickyNoteService.create(note.getNoteTitle(), note.getNoteMessage(), note.getNoteComplete());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("DeleteNote/{id}")
    public ResponseEntity<List> deleteStickyNote(@PathVariable(value = "id") Long stickyNoteId) {
        LOGGER.info("Deleting Sticky Note, Delete Note API");
        StickyNote note = stickyNoteService.retrieveById(stickyNoteId).get();
        stickyNoteService.deleteNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("DeleteAllNotes")
    public ResponseEntity<List> deleteAllNotes() {
        LOGGER.info("Deleting ALL Sticky Notes, Delete Note API");
        stickyNoteService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("EditNote")
    public ResponseEntity<List> editStickyNote(@RequestBody StickyNote note) {
        LOGGER.info("Editing Sticky Note, Edit Note API");
        stickyNoteService.updateNoteComplete(note);
        stickyNoteService.editStickyNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}