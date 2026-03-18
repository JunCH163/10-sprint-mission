package com.sprint.mission.discodeit.repository;


import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.ReadStatus;
import com.sprint.mission.discodeit.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {
    void deleteAllByChannelId(UUID channelId);

    boolean existsByUserAndChannel(User user, Channel channel);

    List<ReadStatus> findAllByUser_Id(UUID userId);

    List<ReadStatus> findAllByChannel_Id(UUID channelId);
}
