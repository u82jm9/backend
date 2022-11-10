package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.StickyNote;
import com.homeapp.one.demo.repository.StickyNoteDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StickyNoteService {

    private static final Logger LOGGER = LogManager.getLogger(StickyNoteService.class);

    @Autowired
    private StickyNoteDao stickyNoteDao;

    public void create(StickyNote note) {
        if (retrieveByTitle(note) != null) {
            LOGGER.warn("Sticky note with this title already exists, not creating a new one!");
        } else {
            LOGGER.info("Adding Sticky Note with title: " + note.getTitle());
            note.setMessage(note.getMessage().replace("\n", ". "));
            stickyNoteDao.save(note);
        }
    }

    public List<StickyNote> retrieveAllNotes() {
        List<StickyNote> list = stickyNoteDao.findAll();
        LOGGER.info("Number of notes found: " + list.size());
        return list;
    }

    public StickyNote retrieveByTitle(StickyNote note) {
        StickyNote noteFromDB = stickyNoteDao.findStickyNoteByTitle(note.getTitle());
        if (noteFromDB != null) {
            LOGGER.info("Retrieving by Title, Sticky Note with Title: " + note.getTitle());
            return noteFromDB;
        } else {
            LOGGER.warn("Could not retrieve Sticky Note by title for: " + note.getTitle());
            return null;
        }
    }

    public Optional<StickyNote> retrieveById(Long id) {
        Optional<StickyNote> noteFromDB = stickyNoteDao.findById(id);
        if (noteFromDB.isPresent()) {
            LOGGER.info("Found Sticky Note: " + noteFromDB.get().getTitle());
            return noteFromDB;
        } else {
            LOGGER.warn("Could not find Sticky Note");
            return Optional.empty();
        }
    }

    public void editStickyNote(StickyNote note) {
        LOGGER.warn("Editing Sticky Note: " + note.getTitle());
        stickyNoteDao.save(note);
    }

    public void deleteNote(StickyNote note) {
        stickyNoteDao.delete(note);
    }

    public void deleteAll() {
        stickyNoteDao.deleteAll();
    }
}