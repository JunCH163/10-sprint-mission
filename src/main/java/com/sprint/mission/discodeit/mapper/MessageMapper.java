package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.entity.base.Message;

public class MessageMapper {
    public static MessageResponseDto toDto(Message message) {
        return new MessageResponseDto(
                message.getId(),
                message.getCreatedAt(),
                message.getUpdatedAt(),
                message.getContent(),
                message.getChannelId(),
                message.getAuthorId(),
                message.getAttachmentIds()
        );
    }
}
