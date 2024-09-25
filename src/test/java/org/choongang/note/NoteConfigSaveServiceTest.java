package org.choongang.note;

import org.choongang.member.MemberUtil;
import org.choongang.notedata.controllers.noteadmincontroller.RequestNoteConfig;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.repositories.NoteRepository;
import org.choongang.notedata.services.config.NoteConfigSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class NoteConfigSaveServiceTest {

    @Autowired
    private NoteConfigSaveService noteConfigSaveService;

    @Autowired
    private NoteRepository noteRepository;

    @Mock
    MemberUtil memberUtil;

    @Autowired
    private NoteDataRepository noteDataRepository;

    private Note note;

    @BeforeEach
    void init() {
        note = new Note();
        note.setNid("note");
        note.setNName("노트");

        noteRepository.saveAndFlush(note);
    }

    @Test
    @WithMockUser(username = "test01@test.org")
    void saveTest() {
        // Given
        note = new Note();
        note.setNid("note");
        note.setNName("노트");

        noteRepository.saveAndFlush(note);

        RequestNoteConfig form = new RequestNoteConfig();
//        form.setNoteSeq(1L);
        form.setNid(note.getNid());
        form.setNName("업데이트된 노트 이름");  // NName 값 설정
        form.setCategory("분류1");

        // When
        noteConfigSaveService.save(form);

        // Then
        Optional<Note> savedNote = noteRepository.findById(form.getNid());
        assertTrue(savedNote.isPresent(), "NoteData should not be null");
        assertEquals("업데이트된 노트 이름", savedNote.get().getNName());
    }

}
