package org.choongang.notedata.services;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.services.FileInfoService;
import org.choongang.global.ListData;
import org.choongang.global.Pagination;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.notedata.controllers.NoteDataSearch;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.entities.QNoteData;
import org.choongang.notedata.exceptions.NoteDataNotFoundException;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.services.config.NoteConfigInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

// 작성한 노트 조회 - 한개 조회, 목록 조회
@Service
@RequiredArgsConstructor
@Transactional
public class NoteInfoService {
    private final NoteConfigInfoService configInfoService;
    private final NoteDataRepository noteDataRepository;
    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final MemberUtil memberUtil;


    public NoteData get(Long nid){
            NoteData item = noteDataRepository.findById(nid).orElseThrow(NoteDataNotFoundException::new);

            addInfo(item);

            return item;
    }

    public ListData<NoteData> getList(NoteDataSearch search){

        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;


        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        QNoteData noteData = QNoteData.noteData;

        String nid = search.getNid();
        andBuilder.and(noteData.note.nid.eq(nid));

        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";

        if (skey != null && !skey.isBlank()) {
            StringExpression expression = null;
            if (sopt.equals("SUBJECT")) {
                expression = noteData.subject;
            } else if (sopt.equals("CONTENT")) {
                expression = noteData.content;
            } else if (sopt.equals("SUBJECT_CONTENT")) {
                expression = noteData.subject.concat(noteData.content);
            } else if (sopt.equals("NAME")){
                expression = noteData.username;
            } else { // 통합검색
                expression = noteData.username.concat(noteData.subject).concat(noteData.content).concat(noteData.email);
            }

            if (expression != null) {
                andBuilder.and(expression.contains(skey));
            }
        }

        String category = search.getCategory();
        if (StringUtils.hasText(category)) {
            andBuilder.and(noteData.category.eq(category.trim()));
        }

        /* 검색 처리 E */

        Pageable pageble = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<NoteData> data = noteDataRepository.findAll(andBuilder, pageble);

        long total = noteDataRepository.count(andBuilder);

        List<NoteData> items = data.getContent();
        items.forEach(this::addInfo);

        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    // 추가 정보 처리
    private void addInfo(NoteData item){
        String gid = item.getGid();
        List<FileInfo> editorImages = fileInfoService.getList(gid, "editor");
        List<FileInfo> attachFiles = fileInfoService.getList(gid, "attach");

        item.setEditorImages(editorImages);
        item.setAttachFiles(attachFiles);

        Member member = memberUtil.getMember();
        String email = member.getEmail();
        boolean editable = false;
        if (memberUtil.isAdmin() || item.getEmail().equals(email)) {
            editable = true;
        }

        item.setEditable(editable);

    }
}
