package org.choongang.notedata.controllers.noteadmincontroller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestNoteConfig {

    private Long noteSeq;

    private String mode;
    @NotBlank
    private String nid; // 게시판 아이디

    @NotBlank
    private String nName; // 게시판 이름

    private boolean active; // 사용 여부

    private int rowsPerPage = 20; // 1페이지 게시글 수

    private int pageCount = 10; // 페이지 구간 갯수

    private String category; // 게시판 분류

    private String locationAfterWriting = "list"; // 글 작성 후 이동 위치

    private String skin = "default"; // 스킨
}
