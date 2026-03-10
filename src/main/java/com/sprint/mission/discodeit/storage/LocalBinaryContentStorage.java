package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "discodeit.storage.type", havingValue = "local")
public class LocalBinaryContentStorage implements BinaryContentStorage {
    private final Path root;

    public LocalBinaryContentStorage(@Value("${discodeit.storage.local.root-path}")Path root) {
        this.root = root;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("로컬 저장소 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    private Path resolvePath(UUID id) {
        return root.resolve(id.toString());
    }

    @Override
    public UUID put(UUID id, byte[] data) {
        Path filePath = resolvePath(id);

        try {
            Files.write(filePath, data);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.",e);
        }
    }

    @Override
    public InputStream get(UUID id) {
        Path filePath = resolvePath(id);

        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어오는 중 오류가 발생했습니다. (id: " + id + ")", e);
        }
    }

    @Override
    public ResponseEntity<Resource> download(BinaryContentDto dto) {
        InputStream inputStream = get(dto.id());
        Resource resource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", dto.fileName());
        headers.setContentType(MediaType.parseMediaType(dto.contentType()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(dto.size())
                .body(resource);
    }
}
