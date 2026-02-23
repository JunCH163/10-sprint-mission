package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.service.BinaryContentService;
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

    @Autowired
    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
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
    public ResponseEntity<BinaryContentResponseDto> find(
            @Parameter(description = "조회할 첨부 파일 ID")
            @PathVariable UUID binaryContentId) {
        BinaryContentResponseDto bcDto = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(bcDto);
    }

    // 2. 바이너리 파일 다건 조회
    @GetMapping()
    public ResponseEntity<List<BinaryContentResponseDto>> findAllByIdIn
    (@RequestParam List<UUID> binaryContentIds) {
        List<BinaryContentResponseDto> bcDto = binaryContentService.findAllByIdIn(ids);
        return ResponseEntity.ok(bcDto);
    }
}
