package com.homeapp.backend.models.note;

/**
 * The type Data Transfer Object for Sticky Note.
 * For taking a single message String and breaking it into individual tasks.
 */
public class DTOnote {

    private String noteTitle;

    private String noteMessage;

    private Boolean noteComplete;

    /**
     * Instantiates a new Data Transfer Object for a Sticky Note.
     *
     * @param noteTitle    the note title
     * @param noteMessage  the note message
     * @param noteComplete the note complete
     */
    public DTOnote(String noteTitle, String noteMessage, Boolean noteComplete) {
        this.noteTitle = noteTitle;
        this.noteMessage = noteMessage;
        this.noteComplete = noteComplete;
    }

    /**
     * Gets note title.
     *
     * @return the note title
     */
    public String getNoteTitle() {
        return noteTitle;
    }

    /**
     * Sets note title.
     *
     * @param noteTitle the note title
     */
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    /**
     * Gets note message.
     *
     * @return the note message
     */
    public String getNoteMessage() {
        return noteMessage;
    }

    /**
     * Sets note message.
     *
     * @param noteMessage the note message
     */
    public void setNoteMessage(String noteMessage) {
        this.noteMessage = noteMessage;
    }

    /**
     * Gets note complete.
     *
     * @return the note complete
     */
    public Boolean getNoteComplete() {
        return noteComplete;
    }

    /**
     * Sets note complete.
     *
     * @param noteComplete the note complete
     */
    public void setNoteComplete(Boolean noteComplete) {
        this.noteComplete = noteComplete;
    }

    @Override
    public String toString() {
        return "DTOnote{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteMessage='" + noteMessage + '\'' +
                ", noteComplete='" + noteComplete + '\'' +
                '}';
    }
}