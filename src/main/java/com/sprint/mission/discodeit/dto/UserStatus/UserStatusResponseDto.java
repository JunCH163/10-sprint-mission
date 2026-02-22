package com.sprint.mission.discodeit.dto.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserStatusResponseDto(UUID id,
                                    Instant createdAt,
                                    Instant updatedAt,
                                    UUID userId,
                                    Instant lastActiveAt,
                                    Boolean online) {
}
