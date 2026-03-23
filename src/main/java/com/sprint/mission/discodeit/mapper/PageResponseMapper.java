package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public class PageResponseMapper {
    public static <T> PageResponse<T> fromCursorList(List<T> content,
                                                     Object nextCursor,
                                                     int size,
                                                     boolean hasNext,
                                                     Long totalElements) {
        return new PageResponse<>(
                content,
                nextCursor,
                size,
                hasNext,
                totalElements
        );
    }

}
