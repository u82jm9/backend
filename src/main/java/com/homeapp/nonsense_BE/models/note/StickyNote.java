package com.homeapp.nonsense_BE.models.note;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = ("stickynote"))
public class StickyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stickyNoteGen")
    @SequenceGenerator(name = "stickyNoteGen", sequenceName = "NOTE_SEQ", allocationSize = 1)
    private long stickyNoteId;

    @Column(nullable = false)
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column
    private Map<String, Boolean> messageMap = new HashMap<String, Boolean>();

    @Column
    private boolean complete;

    public StickyNote() {
    }

    public StickyNote(String title, Map<String, Boolean> messageMap, boolean complete) {
        this.title = title;
        this.messageMap = messageMap;
        this.complete = complete;
    }

    public StickyNote(long id, String title, Map<String, Boolean> messageMap, boolean complete) {
        this.stickyNoteId = id;
        this.title = title;
        this.messageMap = messageMap;
        this.complete = complete;
    }

    public long getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(Long id) {
        this.stickyNoteId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Boolean> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, Boolean> messageMap) {
        this.messageMap = messageMap;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "StickyNote{" +
                "stickyNoteId=" + stickyNoteId +
                ", title='" + title + '\'' +
                ", message=" + messageMap +
                ", complete=" + complete +
                '}';
    }
}