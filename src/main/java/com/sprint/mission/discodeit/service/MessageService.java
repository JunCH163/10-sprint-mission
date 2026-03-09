package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageRequestCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageRequestUpdateDto;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto create(MessageRequestCreateDto messageRequestCreateDto, List<MultipartFile> profileImage);
    MessageDto find(UUID id);
    List<MessageDto> findByChannelId(UUID id);
    MessageDto update(UUID messageId, MessageRequestUpdateDto messageRequestUpdateDto);
    void delete(UUID id);
    List<MessageDto> readMessagesByUser(UUID userId);
}
