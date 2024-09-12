package org.choongang.notedata.services.config;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.note.controllers.RequestNoteConfig;
import org.choongang.note.entities.Note;
import org.choongang.note.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// 노트 설정 등록, 수정
@Service
@RequiredArgsConstructor
public class NoteConfigSaveService {

    private final NoteRepository noteRepository;
    private final Utils utils;

    public void save(RequestNoteConfig form) {
        String nid = form.getNid();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Note note = noteRepository.findById(nid).orElseGet(Note::new);

        if (mode.equals("add")) { // 노트 등록 시 nid 설정
            note.setNid(nid);
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
