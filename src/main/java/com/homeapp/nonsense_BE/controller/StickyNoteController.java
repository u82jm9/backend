package com.homeapp.nonsense_BE.controller;

import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
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

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final StickyNoteService stickyNoteService;

    @Autowired
    public StickyNoteController(StickyNoteService stickyNoteService) {
        this.stickyNoteService = stickyNoteService;
    }

    @GetMapping("GetAll")
    public ResponseEntity<List<StickyNote>> getStickyNotes() {
        infoLogger.log("Getting all Sticky Notes, GetAll API");
        List<StickyNote> list = stickyNoteService.retrieveAllNotes();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @PostMapping("AddNote")
    public ResponseEntity<HttpStatus> addStickyNote(@RequestBody DTOnote note) {
        infoLogger.log("Adding new Sticky Note, API");
        stickyNoteService.create(note.getNoteTitle(), note.getNoteMessage(), note.getNoteComplete());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("DeleteNote/{id}")
    public ResponseEntity<HttpStatus> deleteStickyNote(@PathVariable(value = "id") Long stickyNoteId) {
        infoLogger.log("Deleting Sticky Note, API");
        StickyNote note = stickyNoteService.retrieveById(stickyNoteId);
        stickyNoteService.deleteNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("DeleteAllNotes")
    public ResponseEntity<HttpStatus> deleteAllNotes() {
        infoLogger.log("Deleting ALL Sticky Notes, Delete Note API");
        stickyNoteService.deleteAll();
        warnLogger.log("Deleting ALL Sticky Notes, Delete Note API");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("EditNote")
    public ResponseEntity<HttpStatus> editStickyNote(@RequestBody StickyNote note) {
        infoLogger.log("Editing Sticky Note, API");
        stickyNoteService.editStickyNote(note);
        warnLogger.log("Editing note: " + note);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}