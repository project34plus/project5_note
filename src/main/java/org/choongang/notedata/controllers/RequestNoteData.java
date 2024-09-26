package org.choongang.notedata.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestNoteData {

    private Long noteSeq;

    private String mode; //register, update

    @NotBlank
    private String nid; // 노트 ID

    private String category;

    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content;

    private List<FileInfo> editorImages;
    private List<FileInfo> attachFiles;
    private String email;


    private String username; //로그인 회원명
}
