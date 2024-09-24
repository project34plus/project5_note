package org.choongang.notedata.services.config;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.notedata.controllers.noteadmincontroller.RequestNoteConfig;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.exceptions.noteadminexception.NoteNotFoundException;
import org.choongang.notedata.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

// 노트 설정 등록, 수정
@Service
@RequiredArgsConstructor
public class NoteConfigSaveService {

    private final NoteRepository noteRepository;
    private final Utils utils;
    private final MemberUtil memberUtil;

    public void save(RequestNoteConfig form) {

        String mode = Objects.requireNonNullElse(form.getMode(), "register");
        String nid = form.getNid();

        Note note = null;
        if (mode.equals("update") && nid != null) {
              note = noteRepository.findById(nid).orElseThrow(NoteNotFoundException::new);
        } else {
            note = new Note();
            note.setNid(form.getNid());
        }

        note.setNName(form.getNName());
        note.setActive(form.isActive());
        note.setRowsPerPage(form.getRowsPerPage());
        note.setPageCount(form.getPageCount());
        note.setLocationAfterWriting(form.getLocationAfterWriting());
        note.setSkin(form.getSkin());
        note.setCategory(form.getCategory());

        noteRepository.saveAndFlush(note);

    }
}
