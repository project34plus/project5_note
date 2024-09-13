package org.choongang.notedata;

import org.choongang.member.MemberUtil;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.repositories.NoteRepository;
import org.choongang.notedata.services.NoteInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class NoteInfoServiceTest {

    @Autowired
    private NoteInfoService noteInfoService;

    @Autowired
    private NoteRepository noteRepository;

    @Mock
    MemberUtil memberUtil;

    @Autowired
    private NoteDataRepository noteDataRepository;

    private Note note;
    @WithMockUser(username = "test01@test.org")
    @Test
    void saveTest(){
        noteInfoService.get(1L);




    }
}