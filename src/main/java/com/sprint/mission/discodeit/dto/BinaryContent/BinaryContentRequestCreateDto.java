package com.sprint.mission.discodeit.dto.BinaryContent;

import java.io.InputStream;

public record BinaryContentRequestCreateDto(String fileName,
                                            InputStream inputStream,
                                            String contentType,
                                            Long size) {
}
