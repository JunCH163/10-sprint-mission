package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.MessageRequestCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageRequestUpdateDto;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import com.sprint.mission.discodeit.entity.base.Channel;
import com.sprint.mission.discodeit.entity.base.Message;
import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    private final MessageMapper messageMapper;
    private final BinaryContentStorage binaryContentStorage;

    @Transactional
    @Override
    public MessageDto create(MessageRequestCreateDto request, List<MultipartFile> attachments) {
        Validators.validateCreateMessageRequest(request);

        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));

        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널입니다."));

        Validators.validationMessage(request.content());

        List<BinaryContent> attachmentFiles = storeAttachments(attachments);

        Message message = new Message(request.content(), channel, author, attachmentFiles);

        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }

    @Override
    public MessageDto find(UUID id) {
        Message message = validateExistenceMessage(id);
        return messageMapper.toDto(message);
    }

    @Override
    public PageResponse<MessageDto> findByChannelId(UUID channelId, Pageable pageable) {

        Slice<Message> messageSlice = messageRepository.findAllByChannelId(channelId, pageable);

        Slice<MessageDto> dtoSlice = messageSlice.map(messageMapper::toDto);
        return PageResponseMapper.fromSlice(dtoSlice);
    }

    @Transactional
    @Override
    public MessageDto update(UUID messageId, MessageRequestUpdateDto request) {
        Message message = validateExistenceMessage(messageId);

        Optional.ofNullable(request.newContent())
                .ifPresent(cont -> {Validators.requireNotBlank(cont, "content");
                    message.updateContent(cont);
                });

        return messageMapper.toDto(message);
    }

    @Transactional
    public void delete(UUID messageId) {
        Message message = validateExistenceMessage(messageId);

        messageRepository.delete(message);
    }

    public List<MessageDto> readMessagesByUser(UUID userId) {
        return messageRepository.findAllByAuthorId(userId).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    private Message validateExistenceMessage(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메세지 id는 존재하지 않습니다."));
    }

    private List<BinaryContent> storeAttachments(List<MultipartFile> attachments) {
        List<BinaryContent> attachmentFiles = new ArrayList<>();
        if (attachments == null || attachments.isEmpty()) {
            return attachmentFiles;
        }

        for (MultipartFile file : attachments) {
            if (file.isEmpty()) {
                continue;
            }

            if (file.getContentType() == null || file.getContentType().isBlank()) {
                throw new IllegalArgumentException("파일 형식을 알 수 없는 첨부파일이 있습니다.");
            }

            try {
                BinaryContent content = new BinaryContent(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType());
                BinaryContent saved = binaryContentRepository.save(content);
                binaryContentStorage.put(saved.getId(), file.getBytes());
                attachmentFiles.add(saved);
            } catch (IOException e) {
                throw new UncheckedIOException("첨부파일 처리 중 오류가 발생했습니다.", e);
            }
        }
        return attachmentFiles;
    }

}
