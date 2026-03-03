package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.*;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelResponseDto createPublic(PublicChannelRequestCreateDto publicChannelRequestCreateDto);

    ChannelResponseDto createPrivate(PrivateChannelRequestCreateDto privateChannelRequestCreateDto);

    ChannelResponseDto find(UUID id);

    List<ChannelParticipantResponseDto> findAllByUserId(UUID id);

    ChannelResponseDto updateChannel(UUID channelId,ChannelRequestUpdateDto channelRequestUpdateDto);

    void delete(UUID id);
}
