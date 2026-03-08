package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.entity.base.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {
    Optional<UserStatus> findByUserId(UUID userId);

    boolean existsByUser(User user);

    UserStatus user(User user);
}
