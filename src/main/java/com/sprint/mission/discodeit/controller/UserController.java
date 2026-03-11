package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestOnlineUpdateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.user.UserRequestCreateDto;
import com.sprint.mission.discodeit.dto.user.UserRequestUpdateDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserResponseGetDto;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @Autowired
    public UserController(UserService userService, UserStatusService userStatusService) {
        this.userService = userService;
        this.userStatusService = userStatusService;
    }


    // 1. 사용자 등록
    @Operation(summary = "User 등록", operationId = "create")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User가 성공적으로 생성됨"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "같은 email 또는 username를 사용하는 User가 이미 존재함",
                    content =  @Content(examples = @ExampleObject(value = "User with email {email} already exists"))
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> create(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart UserRequestCreateDto userCreateRequest,
            @RequestPart(required = false)
            @Parameter(description = "User 프로필 이미지")
            MultipartFile profile) {
        UserDto urDto = userService.create(userCreateRequest, profile);
        return ResponseEntity.status(201).body(urDto);
    }

    // 2. 사용자 정보 수정
    @Operation(summary = "User 정보 수정", operationId = "update")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User 정보가 성공적으로 수정됨"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "같은 email 또는 username를 사용하는 User가 이미 존재함",
                    content =  @Content(examples = @ExampleObject(value = "User with email {email} already exists"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User를 찾을 수 없음",
                    content =  @Content(examples = @ExampleObject(value = "User with id {userId} not found"))
            )
    })
    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> update(
            @Parameter(description = "수정할 User ID")
            @PathVariable UUID userId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart UserRequestUpdateDto userUpdateRequest,
            @RequestPart(required = false)
            @Parameter(description = "수정할 User 프로필 이미지")
            MultipartFile profile) {
        UserDto urDto = userService.update(userId, userUpdateRequest, profile);
        return ResponseEntity.ok(urDto);
    }

    // 3. 사용자 삭제
    @Operation(summary = "User 삭제", operationId = "delete")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "User가 성공적으로 삭제됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User를 찾을 수 없음",
                    content =  @Content(examples = @ExampleObject(value = "User with id {id} not found"))
            )
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 User ID")
            @PathVariable UUID userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    // 4. 모든 사용자 조회
    @Operation(summary = "전체 User 목록 조회", operationId = "findAll")
    @ApiResponse(
            responseCode = "200",
            description = "User 목록 조회 성공"
            )
    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll() {
       List<UserDto> users = userService.findAll();
       return ResponseEntity.ok(users);
    }

    // 5. 사용자의 온라인 상태를 업데이트
    @Operation(summary = "User 온라인 상태 업데이트", operationId = "updateUserStatusByUserId")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User 온라인 상태가 성공적으로 업데이트됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 User의 UserStatus를 찾을 수 없음",
                    content =  @Content(examples = @ExampleObject(value = "UserStatus with userId {userId} not found"))
            )
    })
    @PatchMapping(value = "/{userId}/userStatus")
    public ResponseEntity<UserStatusDto> updateOnline(
            @Parameter(description = "상태를 변경할 User ID")
            @PathVariable UUID userId,
            @RequestBody UserStatusRequestOnlineUpdateDto userOnlineRequest
            ) {
        UserStatusDto updatedStatus = userStatusService.updateByUserId(userId, userOnlineRequest);
        return ResponseEntity.ok(updatedStatus);
    }
}
