package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "channels")
public class Channel extends BaseUpdatableEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ChannelType type;

    @Column(length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    public Channel(ChannelType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void updateChannelType(ChannelType type) {
        this.type = type;
    }

    public void updateChannelName(String channelName) {
        this.name = channelName;
    }

    public void updateChannelDescription(String channelDescription) {
        this.description = channelDescription;
    }
}
