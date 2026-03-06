package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "binary_contents")
public class BinaryContent extends BaseEntity {

    @Column(nullable = false)
    private  Long size;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, columnDefinition = "bytea")
    private  byte[] bytes;

    @Column(nullable = false,  length = 100)
    private String contentType;

    public BinaryContent(String fileName, Long size, byte[] bytes, String contentType) {
        this.fileName = fileName;
        this.size = size;
        this.bytes = bytes;
        this.contentType = contentType;
    }
}
