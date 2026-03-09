package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusRequestCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusRequestUpdateDto;
import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusDto;
import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.ReadStatus;
import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    private final ReadStatusMapper readStatusMapper;

    @Transactional
    @Override
    public ReadStatusDto create(ReadStatusRequestCreateDto request) {
        Validators.validateCreateReadStatusRequest(request);

        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new IllegalArgumentException("채널 id가 존재하지 않습니다."));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저 Id가 존재하지 않습니다."));

        boolean exists = readStatusRepository.existsByUserAndChannel(user, channel);

        if (exists) {
            throw new IllegalArgumentException("ReadStatus가 이미 존재합니다.");
        }

        ReadStatus readStatus = new ReadStatus(user, channel, request.lastReadAt());
        ReadStatus savedReadStatus = readStatusRepository.save(readStatus);
        return readStatusMapper.toDto(savedReadStatus);
    }

    @Override
    public ReadStatusDto find(UUID id) {
        ReadStatus readStatus = validateExistenceReadStatus(id);
        return readStatusMapper.toDto(readStatus);
    }

    @Override
    public List<ReadStatusDto> findAllByUserId(UUID id) {
       return readStatusRepository.findAllByUser_Id(id).stream()
                .map(readStatusMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public ReadStatusDto update(UUID readStatusId, ReadStatusRequestUpdateDto request) {
        Validators.requireNonNull(request, "request");
        ReadStatus readStatus = validateExistenceReadStatus(readStatusId);

        readStatus.updateLastReadAt(request.newLastReadAt());
        return readStatusMapper.toDto(readStatus);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        ReadStatus readStatus = validateExistenceReadStatus(id);
        readStatusRepository.delete(readStatus);
    }

    private ReadStatus validateExistenceReadStatus(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return readStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ReadStatus가 존재하지 않습니다."));
    }
}
