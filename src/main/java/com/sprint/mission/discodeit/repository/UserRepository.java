package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String userEmail);

    boolean existsByUsername(String username);

    @Query("SELECT r.user FROM ReadStatus r WHERE r.channel.id = :channelId")
    List<User> findUsersByChannelId(@Param("channelId") UUID channelId);

    Optional<User> findByUsername(String username);
}
