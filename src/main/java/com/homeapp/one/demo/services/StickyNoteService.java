package com.homeapp.one.demo.services;

import com.homeapp.one.demo.models.note.StickyNote;
import com.homeapp.one.demo.repository.StickyNoteDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StickyNoteService {

    private static final Logger LOGGER = LogManager.getLogger(StickyNoteService.class);

    @Autowired
    private StickyNoteDao stickyNoteDao;

    public void create(String title, String message, Boolean complete) {
        if (retrieveByTitle(title) != null) {
            LOGGER.warn("Sticky note with this title already exists, not creating a new one!");
        } else {
            String newMessage = message.replace(".\s", "\n");
            LOGGER.info("Adding Sticky Note with title: " + title);
            Map<String, Boolean> map = new HashMap<>();
            String[] array = newMessage.split("\n");
            for (String s : array) {
                map.put(s, complete);
            }
            StickyNote n = new StickyNote();
            n.setTitle(title);
            n.setMessageMap(map);
            n.setComplete(complete);
            stickyNoteDao.save(n);
        }
    }

    public void create(StickyNote note) {
        if (retrieveByTitle(note.getTitle()) != null) {
            LOGGER.warn("Sticky note with this title already exists, not creating a new one!");
        } else {
            LOGGER.info("Adding Sticky Note with title: " + note.getTitle());
            stickyNoteDao.save(note);
        }
    }

    public List<StickyNote> retrieveAllNotes() {
        List<StickyNote> list = stickyNoteDao.findAll();
        LOGGER.info("Number of notes found: " + list.size());
        return list;
    }

    public StickyNote retrieveByTitle(String title) {
        StickyNote noteFromDB = stickyNoteDao.findStickyNoteByTitle(title);
        if (noteFromDB != null) {
            LOGGER.info("Retrieving by Title, Sticky Note with Title: " + title);
            return noteFromDB;
        } else {
            LOGGER.warn("Could not retrieve Sticky Note by title for: " + title);
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

    public void updateNoteComplete(StickyNote note) {
        if(note.getMessageMap().values().contains(false)){
            note.setComplete(false);
            System.out.println("False");
        } else {
            note.setComplete(true);
            System.out.println("True");
        }
    }
}