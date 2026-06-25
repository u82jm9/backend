package com.homeapp.backend.controller;

import com.homeapp.backend.models.note.DTOnote;
import com.homeapp.backend.models.note.StickyNote;
import com.homeapp.backend.services.StickyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * The Sticky Note Controller.
 * Houses multiple APIs relating to a Full Bike design.
 */
@RestController
@RequestMapping("StickyNotes/")
@CrossOrigin(origins = "http://localhost:3000")
public class StickyNoteController {

    private final Logger logger = Logger.getLogger(StickyNoteController.class.getName());
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
        logger.log(INFO, "Getting all Sticky Notes, GetAll API");
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
        logger.log(INFO, "Adding new Sticky Note, API");
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
        logger.log(INFO, "Deleting Sticky Note, API");
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
        logger.log(INFO, "Deleting ALL Sticky Notes, Delete Note API");
        stickyNoteService.deleteAll();
        logger.log(WARNING, "Deleting ALL Sticky Notes, Delete Note API");
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
        logger.log(INFO, "Editing Sticky Note, API");
        stickyNoteService.editStickyNote(note);
        logger.log(WARNING, "Editing note: " + note);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}