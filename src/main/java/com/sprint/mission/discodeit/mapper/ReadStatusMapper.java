package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.entity.base.ReadStatus;

public class ReadStatusMapper {
    public static ReadStatusResponseDto toDto(ReadStatus readStatus) {
        return new ReadStatusResponseDto(
                readStatus.getId(),
                readStatus.getCreatedAt(),
                readStatus.getUpdatedAt(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastReadAt()
        );
    }
}
