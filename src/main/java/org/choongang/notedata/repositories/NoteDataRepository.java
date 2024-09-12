package org.choongang.notedata.repositories;

import org.choongang.notedata.entities.NoteData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoteDataRepository extends JpaRepository<NoteData, Long>, QuerydslPredicateExecutor<NoteData> {
}
