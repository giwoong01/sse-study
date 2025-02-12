package com.sse.sse_study_backend.notification.consumer;

import com.sse.sse_study_backend.notification.application.SseEmitterManager;
import com.sse.sse_study_backend.notification.domain.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SseEmitterManager sseEmitterManager;
    private final EmitterRepository emitterRepository;

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

}
