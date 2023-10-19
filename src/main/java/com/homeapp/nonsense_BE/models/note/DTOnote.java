package com.homeapp.nonsense_BE.models.note;

public class DTOnote {

    private String noteTitle;

    private String noteMessage;

    private Boolean noteComplete;

    public DTOnote() {
    }

    public DTOnote(String noteTitle, String noteMessage, Boolean noteComplete) {
        this.noteTitle = noteTitle;
        this.noteMessage = noteMessage;
        this.noteComplete = noteComplete;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteMessage() {
        return noteMessage;
    }

    public void setNoteMessage(String noteMessage) {
        this.noteMessage = noteMessage;
    }

    public Boolean getNoteComplete() {
        return noteComplete;
    }

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