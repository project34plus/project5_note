package org.choongang.notedata;

import org.choongang.member.MemberUtil;
import org.choongang.notedata.controllers.RequestNoteData;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.repositories.NoteRepository;
import org.choongang.notedata.services.NoteSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class NoteSaveServiceTest {

    @Autowired
    private NoteSaveService noteSaveService;

    @Autowired
    private NoteRepository noteRepository;

    @Mock
    MemberUtil memberUtil;

    @Autowired
    private NoteDataRepository noteDataRepository;

    private Note note;

    @BeforeEach
    void init(){
        note = new Note();
        note.setNid("note");
        note.setNName("노트");

        noteRepository.saveAndFlush(note);
    }

    @Test
    @WithMockUser(username = "test01@test.org")
    void saveTest(){
        note = new Note();
        note.setNid("note");
        note.setNName("노트");

        noteRepository.saveAndFlush(note);
        RequestNoteData form = new RequestNoteData();
        form.setNoteSeq(1L);
        form.setNid(note.getNid());
        form.setUsername("test01@test.org");
        form.setCategory("분류1");
        form.setSubject("제목");
        form.setContent("내용");
        form.setGid("groupId");  // 필수 필드 설정

        // save 메소드를 호출합니다.
        noteSaveService.save(form);

        // 저장된 NoteData를 조회하기 위한 검색조건 설정
        // gid와 nid가 일치하는 NoteData를 조회합니다.
        Optional<NoteData> savedNoteData = noteDataRepository.findById(form.getNoteSeq());
        assertNotNull(savedNoteData, "NoteData should not be null");


    }
}