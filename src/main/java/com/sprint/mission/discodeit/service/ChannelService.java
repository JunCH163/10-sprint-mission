package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.*;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelDto createPublic(PublicChannelRequestCreateDto publicChannelRequestCreateDto);

    ChannelDto createPrivate(PrivateChannelRequestCreateDto privateChannelRequestCreateDto);

    ChannelDto find(UUID id);

    List<ChannelParticipantResponseDto> findAllByUserId(UUID id);

    ChannelDto updateChannel(UUID channelId, ChannelRequestUpdateDto channelRequestUpdateDto);

    void delete(UUID id);
}
