//package org.choongang.notedata;
//
//import org.choongang.member.MemberUtil;
//import org.choongang.notedata.entities.Note;
//import org.choongang.notedata.entities.NoteData;
//import org.choongang.notedata.repositories.NoteDataRepository;
//import org.choongang.notedata.repositories.NoteRepository;
//import org.choongang.notedata.services.NoteInfoService;
//import org.choongang.notedata.services.config.NoteConfigInfoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class NoteInfoServiceTest {
//
//
//    @Autowired
//    private NoteInfoService noteInfoService; ;
//
//    @Autowired
//    private NoteDataRepository notedataRepository;
//
//    @Mock
//    MemberUtil memberUtil;
//
//    private NoteData notedata;
//
//    @BeforeEach
//    void setUp() {
//        notedata = new NoteData();
//        noteata.setGid("groupId");
//        noteData.setCategory("분류1");
//        noteData.setSubject("제목");
//        noteData.setContent("내용");
//        noteData.setEmail("test@example.com");
//        noteData.setUsername("username");
//        // 다른 필드 설정
//        noteRepository.saveAndFlush(note);
//    }
//
//    @WithMockUser(username = "test01@test.org")
//    @Test
//    void Test() {
//        Note result = noteConfigInfoService.get("noteId");
//        assertNotNull(result);
//        assertEquals("Test Note", result.getNName());
//        System.out.println(result);
//    }
//
//
//
//    }
//}