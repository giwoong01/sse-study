package com.sse.sse_study_backend.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private String sseNotificationTopicName;
    private String sseNotificationGroupId;
    private String discordTopicName;
    private String discordGroupId;
    private String broadcastTopicName;
}