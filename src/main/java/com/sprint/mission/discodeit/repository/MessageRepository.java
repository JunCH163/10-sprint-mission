package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    Optional<Instant> findLatestCreatedAtByChannelId(UUID channelId);

    void deleteAllByChannelId(UUID channelId);

    List<Message> findAllByChannelId(UUID channelId);

    List<Message> findAllByAuthorId(UUID userId);

    Message findTopByChannelOrderByCreatedAtDesc(Channel channel);
}
