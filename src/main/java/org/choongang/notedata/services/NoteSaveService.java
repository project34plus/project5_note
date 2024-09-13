package org.choongang.notedata.services;


import lombok.RequiredArgsConstructor;
import org.choongang.file.services.FileUploadDoneService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.notedata.controllers.RequestNoteData;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.exceptions.NoteDataNotFoundException;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

//노트 작성, 수정
@Service
@RequiredArgsConstructor
public class NoteSaveService {
    private final NoteDataRepository noteDataRepository;
    private final FileUploadDoneService uploadDoneService;
    private final MemberUtil memberUtil;

    public void save(RequestNoteData form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        Long nid = form.getNoteSeq();

        NoteData noteData = null;
        if (mode.equals("update") && nid != null) { // 수정
            noteData = noteDataRepository.findById(nid).orElseThrow(NoteDataNotFoundException::new);
        } else { // 추가
            noteData = new NoteData();
            noteData.setGid(form.getGid());
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
