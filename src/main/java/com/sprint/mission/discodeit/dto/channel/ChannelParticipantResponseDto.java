package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.base.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelParticipantResponseDto(UUID id,
                                            ChannelType type,
                                            String name,
                                            String description,
                                            List<UUID> participantIds,
                                            Instant lastMessageAt) {
}
