package com.homeapp.NonsenseBE.controller;

import com.homeapp.NonsenseBE.models.logger.InfoLogger;
import com.homeapp.NonsenseBE.models.logger.WarnLogger;
import com.homeapp.NonsenseBE.models.note.DTOnote;
import com.homeapp.NonsenseBE.models.note.StickyNote;
import com.homeapp.NonsenseBE.services.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Sticky Note Controller.
 * Houses multiple APIs relating to a Full Bike design.
 */
@RestController
@RequestMapping("StickyNotes/")
@CrossOrigin(origins = "http://localhost:3000")
public class StickyNoteController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final StickyNoteService stickyNoteService;

    /**
     * Instantiates a new Sticky note controller.
     * Autowires in a Sticky Note Service for access to the methods.
     *
     * @param stickyNoteService the sticky note service
     */
    @Autowired
    public StickyNoteController(StickyNoteService stickyNoteService) {
        this.stickyNoteService = stickyNoteService;
    }

    /**
     * Gets list of all Sticky Notes.
     *
     * @return the list of Sticky Notes
     * @return HTTP - Status ACCEPTED
     */
    @GetMapping("GetAll")
    public ResponseEntity<List<StickyNote>> getStickyNotes() {
        infoLogger.log("Getting all Sticky Notes, GetAll API");
        List<StickyNote> list = stickyNoteService.retrieveAllNotes();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    /**
     * Adds Sticky Note.
     * FE does not send a complete Note but a specific Note Transfer Object that the Service method unpacks into a full note.
     *
     * @param note the note
     * @return HTTP - Status CREATED
     */
    @PostMapping("AddNote")
    public ResponseEntity<HttpStatus> addStickyNote(@RequestBody DTOnote note) {
        infoLogger.log("Adding new Sticky Note, API");
        stickyNoteService.create(note.getNoteTitle(), note.getNoteMessage(), note.getNoteComplete());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Delete Sticky Note.
     *
     * @param stickyNoteId the sticky note id
     * @return HTTP - Status OK
     */
    @DeleteMapping("DeleteNote/{id}")
    public ResponseEntity<HttpStatus> deleteStickyNote(@PathVariable(value = "id") Long stickyNoteId) {
        infoLogger.log("Deleting Sticky Note, API");
        StickyNote note = stickyNoteService.retrieveById(stickyNoteId);
        stickyNoteService.deleteNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete all Sticky Notes.
     *
     * @return HTTP - Status OK
     */
    @DeleteMapping("DeleteAllNotes")
    public ResponseEntity<HttpStatus> deleteAllNotes() {
        infoLogger.log("Deleting ALL Sticky Notes, Delete Note API");
        stickyNoteService.deleteAll();
        warnLogger.log("Deleting ALL Sticky Notes, Delete Note API");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Edit Sticky Note.
     *
     * @param note the note
     * @return HTTP - Status OK
     */
    @PostMapping("EditNote")
    public ResponseEntity<HttpStatus> editStickyNote(@RequestBody StickyNote note) {
        infoLogger.log("Editing Sticky Note, API");
        stickyNoteService.editStickyNote(note);
        warnLogger.log("Editing note: " + note);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}