package com.homeapp.NonsenseBE.models.note;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Sticky Note object. Uses a HashMap to allow for each individual task to be completed.
 * Single boolean marks that the entire task is completed or not.
 */
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

    /**
     * Instantiates a new Sticky Note.
     */
    public StickyNote() {
    }

    /**
     * Instantiates a new Sticky Note.
     *
     * @param title      the title
     * @param messageMap the message map
     * @param complete   the complete
     */
    public StickyNote(String title, Map<String, Boolean> messageMap, boolean complete) {
        this.title = title;
        this.messageMap = messageMap;
        this.complete = complete;
    }

    /**
     * Instantiates a new Sticky note.
     *
     * @param id         the id
     * @param title      the title
     * @param messageMap the message map
     * @param complete   the complete
     */
    public StickyNote(long id, String title, Map<String, Boolean> messageMap, boolean complete) {
        this.stickyNoteId = id;
        this.title = title;
        this.messageMap = messageMap;
        this.complete = complete;
    }

    /**
     * Gets sticky note id.
     *
     * @return the sticky note id
     */
    public long getStickyNoteId() {
        return stickyNoteId;
    }

    /**
     * Sets sticky note id.
     *
     * @param id the id
     */
    public void setStickyNoteId(Long id) {
        this.stickyNoteId = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets message map.
     *
     * @return the message map
     */
    public Map<String, Boolean> getMessageMap() {
        return messageMap;
    }

    /**
     * Sets message map.
     *
     * @param messageMap the message map
     */
    public void setMessageMap(Map<String, Boolean> messageMap) {
        this.messageMap = messageMap;
    }

    /**
     * Is complete boolean.
     *
     * @return the boolean
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Sets complete.
     *
     * @param complete the complete
     */
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