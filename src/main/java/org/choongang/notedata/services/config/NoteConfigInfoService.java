package org.choongang.notedata.services.config;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.Pagination;
import org.choongang.notedata.controllers.noteadmincontroller.NoteSearch;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.entities.QNote;
import org.choongang.notedata.exceptions.noteadminexception.NoteNotFoundException;
import org.choongang.notedata.repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

// 노트 설정 조회 - 한개 조회, 목록 조회
@Service
@RequiredArgsConstructor
public class NoteConfigInfoService {

    private final NoteRepository noteRepository;
    private final HttpServletRequest request;

    public Note get(String nid) {
        Note note = noteRepository.findById(nid).orElseThrow(NoteNotFoundException::new);

        // 추가 처리

        return note;
    }

    public ListData<Note> getList(NoteSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        QNote note = QNote.note;
        String sopt = search.getSopt();
        String skey = search.getSkey();
        List<String> nid = search.getNid();
        if (nid != null && !nid.isEmpty()) {
            andBuilder.and(note.nid.in(nid));
        }
        sopt = StringUtils.hasText(sopt) ? sopt.trim().toUpperCase() : sopt;
        if (skey != null && !skey.isBlank()) {
            skey = skey.trim();
            StringExpression expression = null;
            if (sopt.equals("NID")) {
                expression = note.nid;
            } else if (sopt.equals("N_NAME")) {
                expression = note.nName;
            } else { // 통합 검색
                expression = note.nName.concat(note.nid);
            }

            if (expression != null) {
                andBuilder.and(expression.contains(skey));
            }
        }
        /* 검색 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Note> data = noteRepository.findAll(andBuilder, pageable);
        long total = noteRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        List<Note> items = data.getContent();

        return new ListData<>(items, pagination);
    }
}