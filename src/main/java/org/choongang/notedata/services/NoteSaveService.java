package org.choongang.notedata.services;


import lombok.RequiredArgsConstructor;
import org.choongang.file.services.FileUploadDoneService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.notedata.controllers.RequestNoteData;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.exceptions.NoteDataNotFoundException;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.choongang.notedata.services.config.NoteConfigInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

//노트 작성, 수정
@Service
@RequiredArgsConstructor
public class NoteSaveService {
    private final NoteDataRepository noteDataRepository;
    private final FileUploadDoneService uploadDoneService;
    private final NoteConfigInfoService configInfo;
    private final MemberUtil memberUtil;

    public void save(RequestNoteData form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        Long noteSeq = form.getNoteSeq();
        //Long nid = form.getNoteSeq();

        NoteData noteData = null;
        if (mode.equals("update") && noteSeq != null) { // 수정
            noteData = noteDataRepository.findById(noteSeq).orElseThrow(NoteDataNotFoundException::new);
        } else { // 추가
            noteData = NoteData.builder()
                    .note(configInfo.get(form.getNid()))
                    .gid(form.getGid())
                    .build();
            if (memberUtil.isLogin()) {
                Member member = memberUtil.getMember();
                noteData.setEmail(member.getEmail());
                noteData.setUsername(member.getUserName());
            }

        }

        /* 추가, 수정 공통 처리 S */
        noteData.setCategory(form.getCategory());
        noteData.setSubject(form.getSubject());
        noteData.setContent(form.getContent());

        /* 추가, 수정 공통 처리 E */

        noteDataRepository.saveAndFlush(noteData);

        // 파일 업로드 완료 처리
        uploadDoneService.process(noteData.getGid());
    }
}
