package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentRequestCreateDto;
import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.util.Validators;
import com.sun.source.tree.BinaryTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.sprint.mission.discodeit.mapper.BinaryContentMapper.toDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    @Transactional
    @Override
    public BinaryContentResponseDto create(BinaryContentRequestCreateDto request) {
        Validators.requireNonNull(request, "request");
        validateBinaryContent(request.bytes(), request.contentType());

        long fileSize = request.bytes().length;

        BinaryContent binaryContent = new BinaryContent(
                request.fileName(),
                fileSize,
                request.bytes(),
                request.contentType()
        );

        BinaryContent savedBinaryContent = binaryContentRepository.save(binaryContent);
        return toDto(savedBinaryContent);
    }

    @Override
    public BinaryContentResponseDto find(UUID id) {
        BinaryContent binaryContent = validateExistenceBinaryContent(id);
        return toDto(binaryContent);
    }

    @Override
    public List<BinaryContentResponseDto> findAllByIdIn(List<UUID> ids) {
        Validators.requireNonNull(ids, "ids");
        return binaryContentRepository.findAllById(ids).stream()
                .map(BinaryContentMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        BinaryContent binaryContent = validateExistenceBinaryContent(id);
        binaryContentRepository.delete(binaryContent);
    }


    private void validateBinaryContent(byte[] bytes, String contentType) {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("첨부파일 데이터가 비어있습니다.");
        }
        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException("첨부파일 contentType이 비어있습니다.");
        }
    }

    private BinaryContent validateExistenceBinaryContent(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return binaryContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BinaryContent가 존재하지 않습니다."));

    }
}
