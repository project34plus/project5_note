package org.choongang.notedata.controllers.noteadmincontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.rests.JSONData;
import org.choongang.notedata.entities.Note;
import org.choongang.notedata.services.config.NoteConfigDeleteService;
import org.choongang.notedata.services.config.NoteConfigInfoService;
import org.choongang.notedata.services.config.NoteConfigSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="NoteAdmin", description = "노트 관리자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoteAdminController {

    private final HttpServletRequest request;
    private final NoteConfigSaveService noteConfigSaveService;
    private final NoteConfigDeleteService noteConfigDeleteService;
    private final NoteConfigInfoService noteConfigInfoService;

    /**
     * Note 설정 등록, 수정  - POST /admin/register, PATCH /admin/update/{nid}  - /admin/save
     * Note 설정 목록 조회 GET /admin/list
     * Note 설정 한개 조회 GET /admin/info/{nid}
     * Note 설정 삭제  DELETE /admin/{nid}
     */

    @Operation(summary = "노트 설정 등록", method="POST")
    @ApiResponse(responseCode = "201")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RequestNoteConfig form) {
        form.setMode("register");
        return save(form);
    }

    @Operation(summary = "노트 설정 수정", method="PATCH")
    @ApiResponse(responseCode = "200")
    @PatchMapping("/update/{nid}")
    public ResponseEntity<Void> update(@PathVariable("nid") String nid, @Valid @RequestBody RequestNoteConfig form) {
        form.setMode("update");
        form.setNid(nid);
        return save(form);
    }

    public ResponseEntity<Void> save(RequestNoteConfig form)  {

        String method = request.getMethod().toUpperCase();
        HttpStatus status = method.equals("PATCH") ? HttpStatus.OK : HttpStatus.CREATED;
        noteConfigSaveService.save(form);
        return ResponseEntity.status(status).build();
    }

    @Operation(summary = "노트 설정 목록", method="GET")
    @ApiResponse(responseCode = "200", description = "노트 목록이 성공적으로 반환되었습니다.")
    @GetMapping("/list")
    public JSONData list(@ModelAttribute NoteSearch search) {
        ListData<Note> data = noteConfigInfoService.getList(search);

        return new JSONData(data);
    }

    @Operation(summary="노트 설정 한개 조회", method="GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/info/{nid}")
    public JSONData info(@PathVariable("nid") String nid) {
        Note item = noteConfigInfoService.get(nid);
        return new JSONData(item);
    }

    @Operation(summary = "노트 설정 삭제", method="DELETE")
    @ApiResponse(responseCode = "200", description = "노트가 성공적으로 삭제되었습니다.")
    @DeleteMapping("/{nid}")
    public void delete(@PathVariable("nid") String nid) {
        // 노트 삭제 처리
        noteConfigDeleteService.delete(nid);
    }
}