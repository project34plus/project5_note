package org.choongang.notedata;

import com.querydsl.core.BooleanBuilder;
import org.choongang.global.ListData;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.notedata.controllers.NoteDataSearch;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.services.NoteInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class NoteInfoServiceTest {

    @Mock
    private NoteDataRepository noteDataRepository;

    @Mock
    private MemberUtil memberUtil;

    @InjectMocks
    private NoteInfoService noteInfoService;

    private NoteData notedata;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notedata = new NoteData();
        notedata.setGid("groupId");
        notedata.setCategory("분류1");
        notedata.setSubject("제목");
        notedata.setContent("내용");
        notedata.setEmail("test@example.com");
        notedata.setUsername("testusername");

        // Mocking memberUtil
        Member mockMember = new Member();
        mockMember.setEmail("test@example.com");

        when(memberUtil.getMember()).thenReturn(mockMember);

        // Mocking repository
        when(noteDataRepository.findAll(any(BooleanBuilder.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(notedata)));
        when(noteDataRepository.count(any(BooleanBuilder.class))).thenReturn(1L);
    }

    @Test
    void testGetList() {
        // NoteDataSearch 객체를 생성하여 검색 조건을 설정합니다.
        NoteDataSearch search = new NoteDataSearch();
        search.setPage(1);
        search.setLimit(10);
        search.setNid(Collections.singletonList("groupId")); // 필터링할 ID를 설정

        // NoteInfoService의 getList 메서드를 호출합니다.
        ListData<NoteData> result = noteInfoService.getList(search);

        // 결과를 검증합니다.
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals("제목", result.getItems().get(0).getSubject());
        assertTrue(result.getItems().get(0).isEditable()); // Mocked member의 이메일과 일치하므로 editable이 true이어야 함
    }
}