package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.auth.AuthResponseDto;
import com.sprint.mission.discodeit.entity.User;

public class AuthMapper {
    public static AuthResponseDto toDto(User user) {
        return new AuthResponseDto(
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
