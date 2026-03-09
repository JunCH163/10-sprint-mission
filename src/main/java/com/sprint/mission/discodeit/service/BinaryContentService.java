package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentRequestCreateDto;
import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContentDto create(BinaryContentRequestCreateDto request);

    BinaryContentDto find(UUID id);

    List<BinaryContentDto> findAllByIdIn(List<UUID> ids);

    void delete(UUID id);
}
