package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(UUID id,
                      String username,
                      String email,
                      BinaryContentDto profile,
                      Boolean online) {
}
