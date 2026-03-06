package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;

public class BinaryContentMapper {
    public static BinaryContentResponseDto toDto(BinaryContent binaryContent) {
        return new BinaryContentResponseDto(
                binaryContent.getId(),
                binaryContent.getCreatedAt(),
                binaryContent.getFileName(),
                binaryContent.getSize(),
                binaryContent.getContentType(),
                binaryContent.getBytes()
        );
    }
}
