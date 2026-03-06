package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelParticipantResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.ChannelType;

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
                channel.getName(),
                channel.getDescription()
        );
    }

    public static ChannelParticipantResponseDto toParticipantDto(Channel channel, Instant lastMessageAt) {
        List<UUID> ids = (channel.getType() == ChannelType.PRIVATE)
                ? channel.getJoinedUserIds()
                : null;
        return new ChannelParticipantResponseDto(
                    channel.getId(),
                    channel.getType(),
                    channel.getName(),
                    channel.getDescription(),
                    ids,
                    lastMessageAt
        );
    }

}
