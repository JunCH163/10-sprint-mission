package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findAllByChannelIdOrderByCreatedAtDesc(UUID channelId, Pageable pageable);

    List<Message> findByChannelIdAndCreatedAtLessThanOrderByCreatedAtDesc(
            UUID channelId,
            LocalDateTime cursor,
            Pageable pageable
    );

    List<Message> findAllByAuthorId(UUID userId);

    Message findTopByChannelOrderByCreatedAtDesc(Channel channel);
}
