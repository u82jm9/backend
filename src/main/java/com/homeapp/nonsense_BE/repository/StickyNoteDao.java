package com.homeapp.nonsense_BE.repository;

import com.homeapp.nonsense_BE.models.note.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteDao extends JpaRepository<StickyNote, Long> {

    StickyNote findStickyNoteByTitle(String title);
}