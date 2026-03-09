package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserRequestCreateDto;
import com.sprint.mission.discodeit.dto.user.UserRequestUpdateDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserResponseGetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto create(UserRequestCreateDto request, MultipartFile profileImage);

    UserDto find(UUID id);

    List<UserDto> findAll();

    UserDto update(UUID userId, UserRequestUpdateDto request, MultipartFile profileImage);

    void delete(UUID id);

    List<UserDto> findUsersByChannel(UUID channelId);

}
