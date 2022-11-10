package com.homeapp.one.demo;

import com.homeapp.one.demo.models.StickyNote;
import com.homeapp.one.demo.services.StickyNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class StickyNoteTest {

    @Autowired
    private StickyNoteService stickyNoteService;

    @BeforeEach
    private void setup() {
        StickyNote note = new StickyNote("Before All Method", "This is the message for the before all method");
        stickyNoteService.create(note);
        StickyNote note2 = new StickyNote("Second Before All Method", "This is the message for the second before all method");
        stickyNoteService.create(note2);
        StickyNote note3 = new StickyNote("Third Before All Method", "This is the message for the third before all method");
        stickyNoteService.create(note3);
    }

    @Test
    public void test_That_Retrieve_All_Returns_List_Of_Notes() {
        List<StickyNote> stickyNoteList = stickyNoteService.retrieveAllNotes();
        assertNotNull(stickyNoteList);
    }

    @Test
    public void test_That_A_StickyNote_Can_be_Created() {
        int notesBefore = stickyNoteService.retrieveAllNotes().size();
        StickyNote note = new StickyNote("Title for test", "This is the message for the first test");
        stickyNoteService.create(note);
        assertTrue(notesBefore < stickyNoteService.retrieveAllNotes().size());
    }

    @Test
    public void test_That_A_StickyNote_Can_Be_Retrieved_By_Id() {
        assertNotNull(stickyNoteService.retrieveById(2L));
    }

    @Test
    public void test_That_A_StickNote_Can_Be_Edited() {
        StickyNote noteBefore = stickyNoteService.retrieveById(2L).get();
        noteBefore.setMessage("This is the edited message for test");
        stickyNoteService.editStickyNote(noteBefore);
        StickyNote noteAfter = stickyNoteService.retrieveById(2L).get();
        assertNotEquals(noteAfter, noteBefore);
    }

    @Test
    public void test_That_A_StickyNote_Can_Be_Retrieved_By_Title() {
        StickyNote testNote = new StickyNote("Before All Method", "Message");
        assertNotNull(stickyNoteService.retrieveByTitle(testNote));
    }

    @Test
    public void test_That_Two_Notes_Cannot_Have_The_Same_Title() {
        StickyNote note1 = new StickyNote("Test Note", "Test Message");
        stickyNoteService.create(note1);
        int notesBefore = stickyNoteService.retrieveAllNotes().size();
        StickyNote note2 = new StickyNote("Test Note", "Different message");
        stickyNoteService.create(note2);
        int notesAfter = stickyNoteService.retrieveAllNotes().size();
        assertEquals(notesAfter, notesBefore);
    }

    @Test
    public void test_That_A_StickyNote_Can_Be_Deleted() {
        int notesBefore = stickyNoteService.retrieveAllNotes().size();
        StickyNote note = stickyNoteService.retrieveById(2L).get();
        stickyNoteService.deleteNote(note);
        int notesAfter = stickyNoteService.retrieveAllNotes().size();
        assertTrue(notesAfter < notesBefore);
    }

    @Test
    public void test_All_StickyNotes_Can_Be_Deleted() {
        StickyNote note = new StickyNote("Delete me!", "I will be deleted, goodbye.");
        stickyNoteService.create(note);
        stickyNoteService.deleteAll();
        assertNotNull(stickyNoteService.retrieveAllNotes());
    }
}