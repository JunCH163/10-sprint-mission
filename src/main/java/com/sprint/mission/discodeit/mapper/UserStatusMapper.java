package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.UserStatus;

public class UserStatusMapper {

    public static UserStatusResponseDto toDto(UserStatus userStatus, Boolean online) {
        return new UserStatusResponseDto(
                userStatus.getId(),
                userStatus.getCreatedAt(),
                userStatus.getUpdatedAt(),
                userStatus.getUserId(),
                userStatus.getLastSeenAt(),
                online
        );
    }

}
