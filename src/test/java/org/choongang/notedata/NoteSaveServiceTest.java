package org.choongang.notedata;

import org.choongang.notedata.controllers.RequestNoteData;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.repositories.NoteRepository;
import org.choongang.notedata.services.NoteSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class NoteSaveServiceTest {

    @Autowired
    private NoteSaveService noteSaveService;

    @Autowired
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void init(){
        note = new Note();
        note.setNid("note");
        note.setNName("노트");

        noteRepository.saveAndFlush(note);

    }

    @Test
    void saveTest(){
        RequestNoteData form = new RequestNoteData();
        form.setNid(note.getNid());
        form.setCategory("분류1");
        form.setSubject("제목");
        form.setContent("내용");
        form.setGid("groupId");  // 필수 필드 설정

        NoteData data = noteSaveService.save(form);
        System.out.println(data);

        form.setMode("register");
        form.setNoteSeq(data.getNoteSeq());

    }

}