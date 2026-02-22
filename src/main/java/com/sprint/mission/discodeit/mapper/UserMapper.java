package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseGetDto;
import com.sprint.mission.discodeit.entity.User;

public class UserMapper {

    public static UserResponseGetDto toDto(User user, Boolean online) {
        return new UserResponseGetDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUserName(),
                user.getUserEmail(),
                user.getProfileId(),
                online
        );
    }

    public static UserResponseDto toCreateDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPassword(),
                user.getProfileId()
        );
    }
}
