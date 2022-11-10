package com.homeapp.one.demo.repository;

import com.homeapp.one.demo.models.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyNoteDao extends JpaRepository<StickyNote, Long> {

    StickyNote findStickyNoteByTitle(String title);
}