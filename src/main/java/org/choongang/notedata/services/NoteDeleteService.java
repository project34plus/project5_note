package org.choongang.notedata.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.services.FileDeleteService;
import org.choongang.notedata.entities.NoteData;
import org.choongang.notedata.exceptions.NoteDataNotFoundException;
import org.choongang.notedata.repositories.NoteDataRepository;
import org.springframework.stereotype.Service;
// 작성한 노트 삭제 - *첨부된 파일도 함께 삭제*
@Service
@RequiredArgsConstructor
public class NoteDeleteService {
    private final NoteDataRepository noteDataRepository;
    private final FileDeleteService deleteService;


    public void delete(Long noteSeq){
        NoteData noteData = noteDataRepository.findById(noteSeq).orElseThrow(NoteDataNotFoundException::new);



        //파일 삭제
        String gid = noteData.getGid();
        deleteService.delete(gid);

        //노트삭제
        noteDataRepository.deleteById(noteSeq);
        noteDataRepository.flush();


    }
}
