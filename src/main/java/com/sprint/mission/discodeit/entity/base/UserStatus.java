package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_statuses")
public class UserStatus extends BaseUpdatableEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant lastActiveAt;

    public UserStatus(Instant lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public boolean isOnline() {
        return lastActiveAt != null &&
                lastActiveAt.isAfter(Instant.now().minusSeconds(5 * 60));
    }

    public void updateLastActiveAt(Instant newTime) {
        this.lastActiveAt = newTime;
    }

    public void updateLastActiveAt() {
        this.lastActiveAt = Instant.now();
    }

    void setUser(User user) {
        this.user = user;
    }
}
