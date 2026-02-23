package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelRequestUpdateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelRequestCreateDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelRequestCreateDto;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<ChannelResponseDto> createPublic(
            @RequestBody PublicChannelRequestCreateDto requestCreateDto) {
        ChannelResponseDto crDto = channelService.createPublic(requestCreateDto);
        return ResponseEntity.ok(crDto);
    }


    // 2. 비공개 채널 생성
    @Operation(summary = "Private Channel 생성", operationId = "create_4")
    @ApiResponse(
            responseCode = "201",
            description = "Private Channel이 성공적으로 생성됨."
    )
    @PostMapping(value = "/private")
    public ResponseEntity<ChannelResponseDto> createPrivate(@RequestBody PrivateChannelRequestCreateDto requestCreateDto) {
        ChannelResponseDto crDto = channelService.createPrivate(requestCreateDto);
        return ResponseEntity.ok(crDto);
    }

    // 3. 공개 채널 정보 수정
    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<ChannelResponseDto> update(@RequestBody ChannelRequestUpdateDto requestUpdateDto) {
        ChannelResponseDto crDto = channelService.updateChannel(requestUpdateDto);
        return ResponseEntity.ok(crDto);
    }

    // 4. 채널 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestParam UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity.ok().build();
    }

    // 5. 특정 사용자가 볼 수 있는 모든 채널 목록을 조회
    @RequestMapping(value = "/findAllByUserId", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelResponseDto>> findAllByUserId(@RequestParam UUID id) {
        List<ChannelResponseDto> crDto = channelService.findAllByUserId(id);
        return ResponseEntity.ok(crDto);
    }

}
