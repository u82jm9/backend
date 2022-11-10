package com.homeapp.one.demo.models;

import javax.persistence.*;

@Entity
@Table(name = ("stickynote"))
public class StickyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stickyNoteGen")
    @SequenceGenerator(name = "stickyNoteGen", sequenceName = "NOTE_SEQ", allocationSize = 1)
    private long stickyNoteId;

    @Column(nullable = false)
    private String title;

    @Column
    private String message;

    public StickyNote() {
    }

    public StickyNote(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public long getStickyNoteId() {
        return stickyNoteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StickyNote{" +
                "stickyNoteId=" + stickyNoteId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}