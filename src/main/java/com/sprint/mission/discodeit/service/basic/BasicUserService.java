package com.sprint.mission.discodeit.service.basic;
import com.sprint.mission.discodeit.dto.user.UserRequestCreateDto;
import com.sprint.mission.discodeit.dto.user.UserRequestUpdateDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseGetDto;
import com.sprint.mission.discodeit.entity.base.BinaryContent;
import com.sprint.mission.discodeit.entity.base.User;
import com.sprint.mission.discodeit.entity.base.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sprint.mission.discodeit.mapper.UserMapper.toCreateDto;
import static com.sprint.mission.discodeit.mapper.UserMapper.toDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Transactional
    @Override
    public UserResponseDto create(UserRequestCreateDto request, MultipartFile profileImage) {
            Validators.validationUser(request.username(), request.email(), request.password());
            validateDuplicationUserName(request.username());
            validateDuplicationEmail(request.email());

        BinaryContent profile = saveProfileImage(profileImage);

        User user = new User(
                request.username(),
                request.email(),
                request.password(),
                profile
        );

        UserStatus userStatus = new UserStatus(Instant.now());
        user.setStatus(userStatus);

        User savedUser = userRepository.save(user);
        return toCreateDto(savedUser);
    }

    @Override
    public UserResponseGetDto find(UUID id) {
        User user = validateExistenceUser(id);
        boolean online = resolveOnline(id);
        return toDto(user, online);
    }



    // TODO: N+1 해결할 것
    @Override
    public List<UserResponseGetDto> findAll() {
        return userRepository.findAll().stream()
                .map(u -> toDto(u, resolveOnline(u.getId())))
                .toList();
    }

    @Transactional
    @Override
    public UserResponseDto update(UUID userId, UserRequestUpdateDto request, MultipartFile profileImage) {
        Validators.requireNonNull(request, "request");
        User user = validateExistenceUser(userId);

        Optional.ofNullable(request.newUsername())
                .ifPresent(name -> {Validators.requireNotBlank(name, "userName");
                        validateDuplicationUserName(name);
                        user.updateUserName(name);
                });
        Optional.ofNullable(request.newEmail())
                .ifPresent(email -> {Validators.requireNotBlank(email, "userEmail");
                        validateDuplicationEmail(email);
                        user.updateEmail(email);
                });
        Optional.ofNullable(request.newPassword())
                .ifPresent(password -> {Validators.requireNotBlank(password, "userPassword");
                        user.updatePassword(password);
                });

        BinaryContent newProfile = saveProfileImage(profileImage);

        if (newProfile != null) {
            user.updateProfile(newProfile);
        }

        return toCreateDto(user);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        User user = validateExistenceUser(userId);

        BinaryContent profile = user.getProfile();
        if(profile != null) {
            binaryContentRepository.delete(profile);
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserResponseGetDto> findUsersByChannel(UUID channelId) {
        return userRepository.findUsersByChannelId(channelId).stream()
                .map(u -> toDto(u, resolveOnline(u.getId())))
                .toList();
    }

    private void validateDuplicationEmail(String userEmail) {
        if(userRepository.existsByEmail(userEmail))
        {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }

    private void validateDuplicationUserName(String userName) {
        if(userRepository.existsByUsername(userName))
        {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    private User validateExistenceUser(UUID id) {
        Validators.requireNonNull(id, "id는 null이 될 수 없습니다.");
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 id가 존재하지 않습니다."));

    }

    private boolean resolveOnline(UUID userId) {
        return userStatusRepository.findByUserId(userId)
                .map(UserStatus::isOnline)
                .orElse(false);
    }

    private BinaryContent saveProfileImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return null;
        }
        try {
            BinaryContent binaryContent = new BinaryContent(
                    profileImage.getOriginalFilename(),
                    profileImage.getSize(),
                    profileImage.getBytes(),
                    profileImage.getContentType()
            );
            return binaryContentRepository.save(binaryContent);
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 처리 중 오류가 발생했습니다.", e);
        }
    }

}
