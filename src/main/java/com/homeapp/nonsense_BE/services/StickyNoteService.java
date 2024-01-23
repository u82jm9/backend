package com.homeapp.nonsense_BE.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeapp.nonsense_BE.Exceptions.ExceptionHandler;
import com.homeapp.nonsense_BE.models.logger.InfoLogger;
import com.homeapp.nonsense_BE.models.logger.WarnLogger;
import com.homeapp.nonsense_BE.models.note.StickyNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StickyNoteService {

    private static final ObjectMapper om = new ObjectMapper();
    private static final String JSON_NOTES_FILE = "src/main/resources/notes.json";
    private static final String JSON_NOTES_FILE_BACKUP = "src/main/resources/notes_backup.json";
    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private List<StickyNote> notesList;
    private final ExceptionHandler exceptionHandler;

    @Autowired
    public StickyNoteService(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.notesList = readNotesFile();
    }

    private List<StickyNote> readNotesFile() {
        infoLogger.log("Reading Sticky Notes From File");
        try {
            File file = new File(JSON_NOTES_FILE);
            List<StickyNote> notes = om.readValue(file, new TypeReference<>() {
            });
            warnLogger.log("Returning Populated list of Sticky Notes from file, number of notes: " + notes.size());
            return notes;
        } catch (IOException e) {
            exceptionHandler.handleIOException("Note Service", "Read notes from File", e);
        }
        warnLogger.log("Returning Empty list of Sticky Notes from file");
        return new ArrayList<>();
    }

    public void reloadNotesFromBackup() {
        infoLogger.log("Reloading Sticky Notes From Backup File");
        try {
            deleteAll();
            File file = new File(JSON_NOTES_FILE_BACKUP);
            List<StickyNote> notes = om.readValue(file, new TypeReference<>() {
            });
            writeNotesToFile(notes);
        } catch (IOException e) {
            exceptionHandler.handleIOException("Note Service", "Read notes from Back up File", e);
        }
    }

    public void writeNotesToFile(List<StickyNote> list) {
        infoLogger.log("Writing Sticky Notes back to File");
        try {
            om.writeValue(new File(JSON_NOTES_FILE), list);
            notesList = list;
        } catch (IOException e) {
            exceptionHandler.handleIOException("Note Service", "Write notes to File", e);
        }
    }

    public void create(String title, String message, Boolean complete) {
        infoLogger.log("Creating new Sticky Note");
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
        warnLogger.log("Created note: " + n);
        create(n);
    }

    public void create(StickyNote note) {
        infoLogger.log("Creating new Sticky Note");
        if (checkNoteTitle(note.getTitle())) {
            warnLogger.log("Sticky note with this title already exists, not creating a new one!");
        } else {
            warnLogger.log("Adding Sticky Note with title: " + note.getTitle());
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
        infoLogger.log("Getting all notes");
        notesList.sort((o1, o2) -> Boolean.compare(o1.isComplete(), o2.isComplete()));
        warnLogger.log("Number of notes found: " + notesList.size());
        return notesList;
    }

    public StickyNote retrieveByTitle(String title) {
        infoLogger.log("Retrieving by Title, Sticky Note with Title: " + title);
        StickyNote noteFromFile = notesList.stream().filter(note -> note.getTitle().equals(title)).toList().get(0);
        if (checkNoteTitle(title)) {
            warnLogger.log("Retrieving by Title, Sticky Note: " + noteFromFile);
            return noteFromFile;
        } else {
            warnLogger.log("Could not retrieve Sticky Note by title for: " + title);
            return null;
        }
    }

    public StickyNote retrieveById(Long id) {
        infoLogger.log("Retrieving by ID, Sticky Note with ID: " + id);
        StickyNote noteFromFile = notesList.stream().filter(note -> note.getStickyNoteId() == id).toList().get(0);
        if (checkNoteId(id)) {
            warnLogger.log("Retrieving by ID, Sticky Note: " + noteFromFile);
            return noteFromFile;
        } else {
            warnLogger.log("Could not retrieve Sticky Note by ID for: " + id);
            return null;
        }
    }

    public void editStickyNote(StickyNote note) {
        infoLogger.log("Editing Sticky Note: " + note.getTitle());
        StickyNote noteFromFile = retrieveById(note.getStickyNoteId());
        warnLogger.log("Note Before edit: " + noteFromFile);
        noteFromFile.setTitle(note.getTitle());
        noteFromFile.setMessageMap(note.getMessageMap());
        noteFromFile = updateNoteComplete(note);
        warnLogger.log("Note After edit: " + noteFromFile);
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
        infoLogger.log("Updating if note is complete or not");
        if (note.getMessageMap().containsValue(false)) {
            warnLogger.log("Setting note to not complete, note: " + note);
            note.setComplete(false);
        } else {
            warnLogger.log("Setting note to complete, note: " + note);
            note.setComplete(true);
        }
        return note;
    }
}