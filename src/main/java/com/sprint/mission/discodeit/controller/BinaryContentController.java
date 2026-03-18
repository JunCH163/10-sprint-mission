package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "BinaryContent", description = "첨부 파일 API")
@RestController
@RequestMapping("/api/binaryContents")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;
    private final BinaryContentStorage binaryContentStorage;

    @Autowired
    public BinaryContentController(BinaryContentService binaryContentService, BinaryContentStorage binaryContentStorage) {
        this.binaryContentService = binaryContentService;
        this.binaryContentStorage = binaryContentStorage;
    }

    // 1. 바이너리 파일 단건 조회
    @Operation(summary = "첨부 파일 조회", operationId = "find")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "첨부 파일 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "첨부 파일을 찾을 수 없음",
                    content =  @Content(examples = @ExampleObject(value = "BinaryContent with id {binaryContentId} not found"))
            )
    })
    @GetMapping(value = "/{binaryContentId}")
    public ResponseEntity<BinaryContentDto> find(
            @Parameter(description = "조회할 첨부 파일 ID")
            @PathVariable UUID binaryContentId) {
        BinaryContentDto bcDto = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(bcDto);
    }

    // 2. 바이너리 파일 다건 조회
    @Operation(summary = "여러 첨부 파일 조회", operationId = "findAllByIdIn")
    @ApiResponse(
                    responseCode = "200",
                    description = "첨부 파일 목록 조회 성공"
            )
    @GetMapping()
    public ResponseEntity<List<BinaryContentDto>> findAllByIdIn
    ( @Parameter(description = "조회할 첨부 파일 ID 목록")
      @RequestParam List<UUID> binaryContentIds) {
        List<BinaryContentDto> bcDto = binaryContentService.findAllByIdIn(binaryContentIds);
        return ResponseEntity.ok(bcDto);
    }

    // 3. 파일 다운로드
    @Operation(summary = "파일 다운로드", operationId = "download")
    @ApiResponse(
            responseCode = "200",
            description = "파일 다운로드 성공",
            content =  @Content(examples = @ExampleObject(value = "string"))
    )
    @GetMapping(value = "/{binaryContentId}/download")
    public ResponseEntity<?> download(
            @Parameter(description = "다운로드할 파일 ID")
            @PathVariable UUID binaryContentId) {

        BinaryContentDto bcDto = binaryContentService.find(binaryContentId);

        return binaryContentStorage.download(bcDto);
    }
}
