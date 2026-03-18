package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.Message;
import com.sprint.mission.discodeit.entity.base.ReadStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ChannelMapper {

    @Autowired
    protected ReadStatusRepository readStatusRepository;

    @Autowired
    protected MessageRepository messageRepository;

    @Autowired
    protected UserMapper userMapper;


    @Mapping(target = "participants", expression = "java(getParticipants(channel))")
    @Mapping(target = "lastMessageAt", expression = "java(getLastMessageAt(channel))")
    public abstract ChannelDto toDto(Channel channel);

    protected List<UserDto> getParticipants(Channel channel) {
        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannel_Id(channel.getId());

        return readStatuses.stream()
                .map(ReadStatus::getUser)
                .map(userMapper::toDto)
                .toList();
    }

    protected Instant getLastMessageAt(Channel channel) {
        Message lastMessage = messageRepository.findTopByChannelOrderByCreatedAtDesc(channel);
        return lastMessage != null ? lastMessage.getCreatedAt() : null;
    }
}
