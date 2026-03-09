package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestCreateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestOnlineUpdateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusRequestUpdateDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusDto;
import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.entity.base.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    private final UserStatusMapper userStatusMapper;

    @Transactional
    @Override
    public UserStatusDto create(UserStatusRequestCreateDto request) {
        Validators.requireNonNull(request, "request");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        boolean exists = userStatusRepository.existsByUser(user);

        if(exists) {
            throw new IllegalArgumentException("UserStatus가 이미 존재합니다.");
        }
        UserStatus userStatus = new UserStatus(Instant.now());
        user.setStatus(userStatus);

        UserStatus savedUserStatus = userStatusRepository.save(userStatus);
        return userStatusMapper.toDto(savedUserStatus);
    }

    @Override
    public UserStatusDto find(UUID id) {
        UserStatus userStatus = validateExistenceUserStatus(id);
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    public List<UserStatusDto> findAll() {
        return userStatusRepository.findAll().stream()
                .map(userStatusMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void update(UserStatusRequestUpdateDto request) {
        Validators.requireNonNull(request, "request");
        UserStatus userStatus = validateExistenceUserStatus(request.id());
        userStatus.updateLastActiveAt();
    }

    @Transactional
    @Override
    public void updateByUserId(UUID userid, UserStatusRequestOnlineUpdateDto request) {
        Validators.requireNonNull(userid, "userid는 null이 될 수 없습니다.");
        UserStatus status = userStatusRepository.findByUserId(userid)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 상태 정보를 찾을 수 없습니다."));

        status.updateLastActiveAt(request.newLastActiveAt());
    }

    @Override
    public void delete(UUID id) {
        UserStatus userStatus = validateExistenceUserStatus(id);
        userStatusRepository.delete(userStatus);
    }

    private UserStatus validateExistenceUserStatus(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserStatus가 존재하지 않습니다."));

    }

}
