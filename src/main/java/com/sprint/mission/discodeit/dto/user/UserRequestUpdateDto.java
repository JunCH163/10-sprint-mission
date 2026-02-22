package com.sprint.mission.discodeit.dto.user;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "수정할 User 정보")
public record UserRequestUpdateDto(String userName,
                                   String userEmail,
                                   String userPassword,
                                   Boolean online) {
}
