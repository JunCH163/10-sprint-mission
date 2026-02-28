package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.message.MessageRequestCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageRequestUpdateDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Message", description = "Message API")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 1. 메시지 생성
    @Operation(summary = "Message 생성", operationId = "create_2")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Message가 성공적으로 생성됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel 또는 User를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Channel | Author with id {channelId | authorId} not found"))
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> create(
            @Parameter(description = "Message 생성 정보", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart MessageRequestCreateDto messageCreateRequest,
            @Parameter(description = "Message 첨부 파일들")
            @RequestPart(value = "attachments", required = false)
            List<MultipartFile> attachments) {
        MessageResponseDto messageResponseDto = messageService.create(messageCreateRequest, attachments);
        return ResponseEntity.status(201).body(messageResponseDto);
    }

    // 2. 메시지 수정
    @Operation(summary = "Message 내용 수정", operationId = "update_2")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message가 성공적으로 수정됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Message with id {messageId} not found"))
            )
    })
    @PatchMapping(value = "/{messageId}")
    public ResponseEntity<MessageResponseDto> update(
            @Parameter(description = "수정할 Message ID")
            @PathVariable UUID messageId,
            @RequestBody MessageRequestUpdateDto messageRequestDto) {
        MessageResponseDto messageResponseDto = messageService.update(messageId, messageRequestDto);
        return ResponseEntity.ok(messageResponseDto);
    }

    // 3. 메시지 삭제
    @Operation(summary = "Message 삭제", operationId = "delete_1")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Message가 성공적으로 삭제됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Message with id {messageId} not found"))
            )
    })
    @DeleteMapping(value = "/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable UUID messageId) {
        messageService.delete(messageId);
        return ResponseEntity.noContent().build();
    }

    // 4. 특정 채널의 메시지 목록 조회
    @Operation(summary = "Channel의 Message 목록 조회", operationId = "findAllByChannelId")
    @ApiResponse(
            responseCode = "200",
            description = "Message 목록 조회 성공"
    )
    @GetMapping()
    public ResponseEntity<List<MessageResponseDto>> findByChannelId(
            @Parameter(description = "조회할 Channel ID")
            @RequestParam UUID channelId) {
        List<MessageResponseDto> mrDto = messageService.findByChannelId(channelId);
        return ResponseEntity.ok(mrDto);
    }


}
