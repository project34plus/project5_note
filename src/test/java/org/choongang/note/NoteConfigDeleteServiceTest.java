package org.choongang.note;

import org.choongang.notedata.entities.Note;
import org.choongang.notedata.repositories.NoteRepository;
import org.choongang.notedata.services.config.NoteConfigDeleteService;
import org.choongang.notedata.services.config.NoteConfigSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class NoteConfigDeleteServiceTest {

    @Autowired
    private NoteConfigDeleteService noteConfigDeleteService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteConfigSaveService noteConfigSaveService;

    private Note note;

    @BeforeEach
    void setUp() {
        // Note 객체를 새로 생성하고 저장합니다.
        note = new Note();
        note.setNid("groupId");
        note.setCategory("분류1");
        note.setNName("테스트 노트"); // n_name 필드 설정
        note.setActive(true); // 다른 필드도 필요한 경우 설정합니다.

        note = noteRepository.saveAndFlush(note);
    }


    @Test
    void deleteExistingNoteData() {
        // 노트 삭제
        noteConfigDeleteService.delete(note.getNid());

        // 삭제 확인
        boolean exists = noteRepository.existsById(note.getNid());
        assertFalse(exists, "Note가 삭제되지 않았습니다.");
    }
}

