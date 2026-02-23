package com.sprint.mission.discodeit.dto.BinaryContent;

public record BinaryContentRequestCreateDto(String fileName,
                                            byte[] bytes,
                                            String contentType) {
}
