package com.sse.sse_study_backend.notification.consumer;

import com.sse.sse_study_backend.global.discord.util.DiscordWebhookUtil;
import com.sse.sse_study_backend.notification.application.SseEmitterManager;
import com.sse.sse_study_backend.notification.domain.repository.EmitterRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final RedisTemplate<String, String> redisTemplate;

    private final SseEmitterManager sseEmitterManager;
    private final EmitterRepository emitterRepository;
    private final DiscordWebhookUtil discordWebhookUtil;

    @KafkaListener(topics = "sse_notifications", groupId = "sse-group")
    public void listen(String message) {
        String[] parts = message.split("\\|");

        if (parts.length < 2) {
            return;
        }

        String emitterId = parts[0];
        String content = parts[1];

        SseEmitter emitter = emitterRepository.findById(emitterId);

        if (emitter != null) {
            sseEmitterManager.sendToClient(emitter, emitterId, content);
        }
    }

    @KafkaListener(topics = "discord_notifications", groupId = "discord-group")
    public void discordWebhook(String message) {
        String[] parts = message.split("\\|");

        if (parts.length < 2) {
            return;
        }

        String memberId = parts[0];
        String content = parts[1];

        discordWebhookUtil.sendDiscordMessage(content, memberId);
    }

    @KafkaListener(topics = "broadcast", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listenAll(String message) {
        Map<Object, Object> connectedUsers = redisTemplate.opsForHash().entries("sse_connections");

        for (Map.Entry<Object, Object> entry : connectedUsers.entrySet()) {
            String emitterId = (String) entry.getKey();

            SseEmitter emitter = emitterRepository.findById(emitterId);
            if (emitter != null) {
                sseEmitterManager.sendToClient(emitter, emitterId, message);
            }
        }
    }

}