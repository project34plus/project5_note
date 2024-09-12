package org.choongang.notedata.services.config;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.repositories.NoteRepository;
import org.springframework.stereotype.Service;

// 노트 설정 삭제
@Service
@RequiredArgsConstructor
public class NoteConfigDeleteService {

    private final NoteRepository noteRepository;
    private final NoteConfigInfoService configInfoService;
    private final Utils utils;

    /*
    * 게시판 삭제
    * */

    public void delete(String nid) {
        Note note = configInfoService.get(nid);

        noteRepository.delete(note);

        noteRepository.flush();


    }
}