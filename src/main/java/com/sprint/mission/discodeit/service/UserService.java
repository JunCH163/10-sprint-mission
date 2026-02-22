package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserRequestCreateDto;
import com.sprint.mission.discodeit.dto.user.UserRequestUpdateDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseGetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto create(UserRequestCreateDto request, MultipartFile profileImage);

    UserResponseGetDto find(UUID id);

    List<UserResponseGetDto> findAll();

    UserResponseDto update(UUID userId, UserRequestUpdateDto request, MultipartFile profileImage);

    void delete(UUID id);

    List<UserResponseGetDto> findUsersByChannel(UUID channelId);



}
