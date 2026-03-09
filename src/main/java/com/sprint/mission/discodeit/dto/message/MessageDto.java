package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record MessageDto(UUID id,
                         Instant createdAt,
                         Instant updatedAt,
                         String content,
                         UUID channelId,
                         UserDto author,
                         List<BinaryContentDto> attachments) {
}
