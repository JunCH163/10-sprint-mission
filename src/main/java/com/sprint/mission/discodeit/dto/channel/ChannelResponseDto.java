package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.base.ChannelType;

import java.time.Instant;
import java.util.UUID;

public record ChannelResponseDto(UUID id,
                                 Instant createdAt,
                                 Instant updatedAt,
                                 ChannelType type,
                                 String name,
                                 String description) {
}
