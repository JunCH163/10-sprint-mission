package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "messages")
public class Message extends BaseUpdatableEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<BinaryContent> attachments = new ArrayList<>();


    public Message(String content, Channel channel, User author, List<BinaryContent> attachments) {
        this.content = content;
        this.channel = channel;
        this.author = author;
        if (attachments != null) {
            this.attachments.addAll(attachments);
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addAttachment(BinaryContent attachment) {
        if (attachment != null) {
            this.attachments.add(attachment);
        }
    }
}
