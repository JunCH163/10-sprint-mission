package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {

    @Query("SELECT r.channel FROM ReadStatus r WHERE r.user.id = :userId")
    List<Channel> findChannelsByUserId(@Param("userId") UUID userId);

}
