package org.choongang.note.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.choongang.note.services.config.NoteConfigDeleteService;
import org.choongang.note.services.config.NoteConfigSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Note", description = "노트 API")
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final HttpServletRequest request;
    private final NoteConfigDeleteService noteConfigDeleteService;
    private final NoteConfigSaveService noteConfigSaveService;

    /**
     * 1. 노트 설정 조회 - GET /config/{nid}
     * 2. 노트 작성, 수정  POST /write/{nid},  PATCH /update/{noteSeq}  -> /save
     * 3. 노트 하나 조회   GET /info/{noteSeq}
     * 4. 노트 목록 조회   GET /list/{nid}
     * 5. 노트 삭제   DELETE /{noteSeq}
     */
    @Operation(summary = "노트 설정 조회", method="GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name="nid", required = true, description = "경로변수, 노트 설정 아이디")
    @GetMapping("/config/{nid}")
    public JSONData config(@PathVariable("nid") String nid) {

        return null;
    }

    @Operation(summary = "노트 작성", method="POST")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name = "nid", required = true, description = "경로변수, 노트 설정 아이디")
    })
    @PostMapping("/write/{nid}")
    public ResponseEntity<Void> write(@PathVariable("nid") String nid) {

        return save();
    }

    @Operation(summary = "노트 수정", method="PATCH")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name = "noteSeq", required = true, description = "경로변수, 노트 작성 번호")
    })
    @PatchMapping("/update/{noteSeq}")
    public ResponseEntity<Void> update(@PathVariable("noteSeq") Long noteSeq) {

        return save();
    }

    public ResponseEntity<Void> save() {

        String method = request.getMethod().toUpperCase();
        HttpStatus status = method.equals("PATCH") ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).build();
    }

    @Operation(summary = "노트 한개 조회",  method = "GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name="noteSeq", required = true, description = "경로 변수, 노트 작성 번호")
    @GetMapping("/info/{noteSeq}")
    public JSONData info(@PathVariable("noteSeq") Long noteSeq) {
        return null;
    }

    @Operation(summary = "노트 목록", method="GET")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name = "nid", required = true, description = "경로변수, 노트 설정 아이디")
    })
    @GetMapping("/list/{nid}")
    public JSONData list(@PathVariable("nid") String nid, @RequestParam("chk")List<Integer> chks) {

        return null;
    }

    @Operation(summary = "노트 삭제", method = "DELETE")
    @ApiResponse(responseCode = "200")
    @Parameter(name="noteSeq", required = true, description = "경로 변수, 노트 작성 번호")
    @DeleteMapping("/{noteSeq}")
    public void delete(@PathVariable("noteSeq")Long noteSeq, @RequestParam("chk")List<Integer> chks) {
    }
}