package com.sprint.mission.discodeit.dto.BinaryContent;

import java.io.InputStream;

public record BinaryContentRequestCreateDto(String fileName,
                                            byte[] data,
                                            String contentType,
                                            Long size) {
}
