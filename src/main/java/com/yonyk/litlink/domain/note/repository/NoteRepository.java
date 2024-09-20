package com.yonyk.litlink.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.litlink.domain.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {}
