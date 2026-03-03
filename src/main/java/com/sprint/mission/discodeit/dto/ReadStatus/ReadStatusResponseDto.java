package com.sprint.mission.discodeit.dto.ReadStatus;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusResponseDto(UUID id,
                                    Instant createdAt,
                                    Instant updatedAt,
                                    UUID userId,
                                    UUID channelId,
                                    Instant lastReadAt) {
}
