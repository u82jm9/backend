package com.homeapp.nonsense_BE.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StickyNoteService {

    private static final Logger LOGGER = LogManager.getLogger(StickyNoteService.class);
    private static final ObjectMapper om = new ObjectMapper();
    private static final String JSON_NOTES_FILE = "src/main/resources/notes.json";
    private List<StickyNote> notesList;

    private StickyNoteService() {
        this.notesList = readNotesFile();
    }

    private List<StickyNote> readNotesFile() {
        LOGGER.info("Reading Sticky Notes From File");
        try {
            File file = new File(JSON_NOTES_FILE);
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.error("Error reading notes from file: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void writeNotesToFile(List<StickyNote> list) {
        try {
            om.writeValue(new File(JSON_NOTES_FILE), list);
            notesList = list;
        } catch (IOException e) {
            LOGGER.error("Error writing notes to file: {}", e.getMessage());
        }
    }

    public void create(String title, String message, Boolean complete) {
        String newMessage = message.replace(".\s", "\n");
        Map<String, Boolean> map = new HashMap<>();
        String[] array = newMessage.split("\n");
        for (String s : array) {
            map.put(s, complete);
        }
        StickyNote n = new StickyNote();
        n.setTitle(title);
        n.setMessageMap(map);
        n.setComplete(complete);
        create(n);
    }

    public void create(StickyNote note) {
        if (checkNoteTitle(note.getTitle())) {
            LOGGER.warn("Sticky note with this title already exists, not creating a new one!");
        } else {
            LOGGER.info("Adding Sticky Note with title: {}", note.getTitle());
            long newId = notesList.size() + 1;
            note.setStickyNoteId(newId);
            notesList.add(note);
            writeNotesToFile(notesList);
        }
    }

    private boolean checkNoteTitle(String title) {
        return notesList.stream().anyMatch(n -> n.getTitle().equals(title));
    }

    private boolean checkNoteId(Long id) {
        return notesList.stream().anyMatch(n -> n.getStickyNoteId() == id);
    }

    public List<StickyNote> retrieveAllNotes() {
        LOGGER.info("Number of notes found: {}", notesList.size());
        notesList.sort((o1, o2) -> Boolean.compare(o1.isComplete(), o2.isComplete()));
        return notesList;
    }

    public StickyNote retrieveByTitle(String title) {
        StickyNote noteFromFile = notesList.stream().filter(note -> note.getTitle().equals(title)).toList().get(0);
        if (checkNoteTitle(title)) {
            LOGGER.info("Retrieving by Title, Sticky Note with Title: {}", title);
            return noteFromFile;
        } else {
            LOGGER.warn("Could not retrieve Sticky Note by title for: {}", title);
            return null;
        }
    }

    public StickyNote retrieveById(Long id) {
        StickyNote noteFromFile = notesList.stream().filter(note -> note.getStickyNoteId() == id).toList().get(0);
        if (checkNoteId(id)) {
            LOGGER.info("Retrieving by ID, Sticky Note with ID: {}", id);
            return noteFromFile;
        } else {
            LOGGER.warn("Could not retrieve Sticky Note by ID for: {}", id);
            return null;
        }
    }

    public void editStickyNote(StickyNote note) {
        LOGGER.warn("Editing Sticky Note: {}", note.getTitle());
        StickyNote noteFromFile = retrieveById(note.getStickyNoteId());
        noteFromFile.setTitle(note.getTitle());
        noteFromFile.setMessageMap(note.getMessageMap());
        noteFromFile = updateNoteComplete(note);
        notesList.removeIf(n -> n.getTitle().equals(note.getTitle()));
        notesList.add(noteFromFile);
        writeNotesToFile(notesList);
    }

    public void deleteNote(StickyNote note) {
        notesList.removeIf(n -> n.getStickyNoteId() == note.getStickyNoteId());
        writeNotesToFile(notesList);
    }

    public void deleteAll() {
        writeNotesToFile(new ArrayList<>());
    }

    private StickyNote updateNoteComplete(StickyNote note) {
        if (note.getMessageMap().containsValue(false)) {
            note.setComplete(false);
        } else {
            note.setComplete(true);
        }
        LOGGER.info("Setting note complete: {}", note.isComplete());
        return note;
    }
}