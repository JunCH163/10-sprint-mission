package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentRequestCreateDto;
import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    private final BinaryContentMapper binaryContentMapper;

    private final BinaryContentStorage binaryContentStorage;

    @Transactional
    @Override
    public BinaryContentDto create(BinaryContentRequestCreateDto request) {
        Validators.requireNonNull(request, "request");
        validateBinaryContent(request.inputStream(), request.size(),request.contentType());

        BinaryContent binaryContent = new BinaryContent(
                request.fileName(),
                request.size(),
                request.contentType()
        );

        BinaryContent savedBinaryContent = binaryContentRepository.save(binaryContent);
        binaryContentStorage.put(savedBinaryContent.getId(), request.inputStream());
        return binaryContentMapper.toDto(savedBinaryContent);
    }

    @Override
    public BinaryContentDto find(UUID id) {
        BinaryContent binaryContent = validateExistenceBinaryContent(id);
        return binaryContentMapper.toDto(binaryContent);
    }

    @Override
    public List<BinaryContentDto> findAllByIdIn(List<UUID> ids) {
        Validators.requireNonNull(ids, "ids");
        return binaryContentRepository.findAllById(ids).stream()
                .map(binaryContentMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        BinaryContent binaryContent = validateExistenceBinaryContent(id);
        binaryContentRepository.delete(binaryContent);
    }


    private void validateBinaryContent(InputStream inputStream, long size, String contentType) {
        if (inputStream == null) {
            throw new IllegalArgumentException("첨부파일 데이터가 비어있습니다.");
        }

        if(size <= 0) {
            throw new IllegalArgumentException("첨부파일 크기가 0입니다.");
        }

        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException("첨부파일 contentType이 비어있습니다.");
        }
    }

    private BinaryContent validateExistenceBinaryContent(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return binaryContentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("BinaryContent가 존재하지 않습니다."));

    }
}
