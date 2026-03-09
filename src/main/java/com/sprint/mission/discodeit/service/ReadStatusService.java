package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusRequestCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusRequestUpdateDto;
import com.sprint.mission.discodeit.dto.ReadStatus.ReadStatusDto;
import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatusDto create(ReadStatusRequestCreateDto request);

    ReadStatusDto find(UUID id);

    List<ReadStatusDto> findAllByUserId(UUID id);

    ReadStatusDto update(UUID readStatusId, ReadStatusRequestUpdateDto request);

    void delete(UUID id);

}
