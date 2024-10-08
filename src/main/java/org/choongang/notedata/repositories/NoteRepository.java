package org.choongang.notedata.repositories;

import org.choongang.notedata.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoteRepository extends JpaRepository<Note, String>, QuerydslPredicateExecutor<Note> {
}
