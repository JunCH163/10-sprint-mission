package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.auth.AuthResponseDto;
import com.sprint.mission.discodeit.entity.base.User;

public class AuthMapper {
    public static AuthResponseDto toDto(User user) {
        return new AuthResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getProfileId()
        );
    }

}
