package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageRequestCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageRequestUpdateDto;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto create(MessageRequestCreateDto messageRequestCreateDto, List<MultipartFile> profileImage);
    MessageDto find(UUID id);
    PageResponse<MessageDto> findByChannelId(UUID channelId, LocalDateTime cursor, int size);
    MessageDto update(UUID messageId, MessageRequestUpdateDto messageRequestUpdateDto);
    void delete(UUID id);
    List<MessageDto> readMessagesByUser(UUID userId);
}
