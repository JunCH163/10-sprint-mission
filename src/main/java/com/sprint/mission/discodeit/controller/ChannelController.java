package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.*;
import com.sprint.mission.discodeit.service.ChannelService;
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

@Tag(name = "Channel", description = "Channel API")
@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;
    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }
    // 1. 공개 채널 생성
    @Operation(summary = "Public Channel 생성", operationId = "create_3")
    @ApiResponse(
            responseCode = "201",
            description = "Public Channel이 성공적으로 생성됨"
    )
    @PostMapping(value = "/public")
    public ResponseEntity<ChannelDto> createPublic(
            @RequestBody PublicChannelRequestCreateDto requestCreateDto) {
        ChannelDto crDto = channelService.createPublic(requestCreateDto);
        return ResponseEntity.status(201).body(crDto);
    }


    // 2. 비공개 채널 생성
    @Operation(summary = "Private Channel 생성", operationId = "create_4")
    @ApiResponse(
            responseCode = "201",
            description = "Private Channel이 성공적으로 생성됨."
    )
    @PostMapping(value = "/private")
    public ResponseEntity<ChannelDto> createPrivate(@RequestBody PrivateChannelRequestCreateDto requestCreateDto) {
        ChannelDto crDto = channelService.createPrivate(requestCreateDto);
        return ResponseEntity.status(201).body(crDto);
    }

    // 3. 공개 채널 정보 수정
    @Operation(summary = "Channel 정보 수정", operationId = "update_3")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 정보가 성공적으로 수정됨"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Private Channel은 수정할 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Private channel cannot be updated"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel을 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Channel with id {channelId} not found"))
            )
    }
    )
    @PatchMapping(value = "/{channelId}")
    public ResponseEntity<ChannelDto> update(
            @Parameter(description = "수정할 Channel ID")
            @PathVariable UUID channelId,
            @RequestBody ChannelRequestUpdateDto requestUpdateDto) {
        ChannelDto crDto = channelService.updateChannel(channelId, requestUpdateDto);
        return ResponseEntity.ok(crDto);
    }

    // 4. 채널 삭제
    @Operation(summary = "Channel 삭제", operationId = "delete_2")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Channel이 성공적으로 삭제됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel을 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = "Channel with id {channelId} not found"))
            )
    })
    @DeleteMapping(value = "/{channelId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 Channel ID")
            @PathVariable UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity.noContent().build();
    }

    // 5. 특정 사용자가 볼 수 있는 모든 채널 목록을 조회
    @Operation(summary = "User가 참여 중인 Channel 목록 조회", operationId = "findAll_1")
    @ApiResponse(
            responseCode = "200",
            description = "Channel 목록 조회 성공"
    )
    @GetMapping()
    public ResponseEntity<List<ChannelDto>> findAllByUserId(
            @Parameter(description = "조회할 User ID")
            @RequestParam UUID userId) {
        List<ChannelDto> crDto = channelService.findAllByUserId(userId);
        return ResponseEntity.ok(crDto);
    }

}
