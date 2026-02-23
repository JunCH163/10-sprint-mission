package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ChannelMapper {
    public static ChannelResponseDto toDto(Channel channel, Instant lastMessageAt) {
        List<UUID> joinedUserIds =
                channel.getType() == ChannelType.PRIVATE ? channel.getJoinedUserIds() : null;
        return new ChannelResponseDto(
                channel.getId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                channel.getType(),
                channel.getChannelName(),
                channel.getChannelDescription()
        );
    }
}
