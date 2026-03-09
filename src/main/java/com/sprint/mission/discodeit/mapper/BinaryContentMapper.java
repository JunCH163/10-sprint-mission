package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class BinaryContentMapper {
    public BinaryContentDto toDto(BinaryContent binaryContent) {
        if (binaryContent == null) {
            return null;
        }

        return BinaryContentDto.builder()
                .id(binaryContent.getId())
                .fileName(binaryContent.getFileName())
                .size(binaryContent.getSize())
                .contentType(binaryContent.getContentType())
                .bytes(binaryContent.getBytes())
                .build();
    }
}
