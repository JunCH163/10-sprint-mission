package com.sprint.mission.discodeit.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User 생성 정보")
public record UserRequestCreateDto(String username,
                                   String email,
                                   String password) {

}
