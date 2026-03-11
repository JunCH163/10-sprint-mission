package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestCreateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestOnlineUpdateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestUpdateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusDto;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusDto create(UserStatusRequestCreateDto request);

    UserStatusDto find(UUID id);

    List<UserStatusDto> findAll();

    UserStatusDto updateByUserId(UUID id, UserStatusRequestOnlineUpdateDto request);

    void delete(UUID id);
}
