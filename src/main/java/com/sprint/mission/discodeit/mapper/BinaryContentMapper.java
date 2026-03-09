package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface BinaryContentMapper  {
    BinaryContentDto toDto(BinaryContent binaryContent);

}
