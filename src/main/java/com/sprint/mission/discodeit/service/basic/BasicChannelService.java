package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.*;
import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.ChannelType;
import com.sprint.mission.discodeit.entity.base.ReadStatus;
import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    private final ChannelMapper channelMapper;

    @Transactional
    @Override
    public ChannelDto createPublic(PublicChannelRequestCreateDto request) {
        Validators.validateCreatePublicChannel(request.name(), request.description());
        Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());

        Channel savedChannel = channelRepository.save(channel);
        return channelMapper.toDto(savedChannel);
    }

    @Transactional
    @Override
    public ChannelDto createPrivate(PrivateChannelRequestCreateDto request) {
        Validators.validateCreatePrivateChannel(request.participantIds());

        List<User> users = userRepository.findAllById(request.participantIds());

        if (users.size() != request.participantIds().size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        Channel channel = new Channel(ChannelType.PRIVATE, null, null);
        Channel savedChannel = channelRepository.save(channel);

        List<ReadStatus> readStatuses = users.stream()
                .map(user -> {
                   return new ReadStatus(user, savedChannel, Instant.now());
                })
                .toList();

        readStatusRepository.saveAll(readStatuses);

        return channelMapper.toDto(savedChannel);
    }


    @Override
    public ChannelDto find(UUID id) {
        Channel channel = validateExistenceChannel(id);
        return channelMapper.toDto(channel);
    }

    // TODO: N+1 해결할 것
    @Override
    public List<ChannelDto> findAllByUserId(UUID id) {
        return channelRepository.findChannelsByUserId(id).stream()
                .map(channelMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public ChannelDto updateChannel(UUID channelId, ChannelRequestUpdateDto request) {
        Validators.requireNonNull(request, "request");
        Channel channel = validateExistenceChannel(channelId);

        if(channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalStateException("PRIVATE 채널은 수정할 수 없습니다.");
        }

        Optional.ofNullable(request.newName())
                .ifPresent(name -> {Validators.requireNotBlank(name, "channelName");
                    channel.updateChannelName(name);
                });
        Optional.ofNullable(request.newDescription()).ifPresent(des -> {
            Validators.requireNotBlank(des, "channelDescription");
            channel.updateChannelDescription(des);
        });



        return channelMapper.toDto(channel);
    }



    @Override
    public void delete(UUID id) {
        Channel channel = validateExistenceChannel(id);
        channelRepository.delete(channel);
    }



    private Channel validateExistenceChannel(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("채널 id가 존재하지 않습니다."));
    }
}