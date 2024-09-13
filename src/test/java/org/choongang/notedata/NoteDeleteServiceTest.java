package org.choongang.notedata;

import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.exceptions.NoteDataNotFoundException;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.services.NoteDeleteService;
import org.choongang.notedata.services.NoteSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class NoteDeleteServiceTest {

    @Autowired
    private NoteDeleteService noteDeleteService;

    @Autowired
    private NoteDataRepository noteDataRepository;

    @Autowired
    private NoteSaveService noteSaveService;

    private NoteData noteData;

    @BeforeEach
    void setUp() {
        // NoteData 객체를 새로 생성하고 저장합니다.
        noteData = new NoteData();
        noteData.setGid("groupId");
        noteData.setCategory("분류1");
        noteData.setSubject("제목");
        noteData.setContent("내용");
        noteData.setEmail("test@example.com");
        noteData.setUsername("username");

        noteData = noteDataRepository.saveAndFlush(noteData);
    }

    @Test
    void deleteExistingNoteData() {
        // 주어진 ID로 NoteData를 삭제하고, 이후에 존재하지 않는지 확인합니다.
        noteDeleteService.delete(noteData.getNoteSeq());
        assertThrows(NoteDataNotFoundException.class, () -> {
            noteDataRepository.findById(noteData.getNoteSeq()).orElseThrow(NoteDataNotFoundException::new);
        });
    }
}