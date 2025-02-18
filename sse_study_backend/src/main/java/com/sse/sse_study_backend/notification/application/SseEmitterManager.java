package com.sse.sse_study_backend.notification.application;

import com.sse.sse_study_backend.member.domain.Member;
import com.sse.sse_study_backend.notification.domain.repository.EmitterRepository;
import com.sse.sse_study_backend.notification.exception.EmitterCallbackException;
import com.sse.sse_study_backend.notification.exception.SendFailedException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60 * 24;
    private static final String SERVER_ID = UUID.randomUUID().toString();

    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final EmitterRepository emitterRepository;

    public SseEmitter connect(final Long memberId) {
        String emitterId = String.valueOf(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        redisTemplate.opsForHash().put("sse_connections", emitterId, SERVER_ID);

        registerEmitterCallbacks(emitter, emitterId);

        sendToClient(emitter, emitterId, "이벤트 스트림 생성 memberId: " + memberId);

        return emitter;
    }

    private void registerEmitterCallbacks(SseEmitter emitter, String emitterId) {
        emitter.onCompletion(() -> {
            log.info("비동기 요청 완료");
            removeEmitter(emitterId);
        });

        emitter.onTimeout(() -> {
            log.info("시간 초과");
            emitter.complete();
            removeEmitter(emitterId);
        });

        emitter.onError((e) -> {
            removeEmitter(emitterId);
            throw new EmitterCallbackException("Emitter 에러발생", e);
        });
    }

    public void send(Member targetMember, String message) {
        String emitterId = String.valueOf(targetMember.getId());
        String serverId = (String) redisTemplate.opsForHash().get("sse_connections", emitterId);

        if (serverId != null) {
            String data = emitterId + "|" + message;
            kafkaTemplate.send("sse_notifications", serverId, data);
        }
    }

    public void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            removeEmitter(emitterId);
            throw new SendFailedException("전송 실패", exception);
        }
    }

    public void removeEmitter(String emitterId) {
        emitterRepository.deleteById(emitterId);
        redisTemplate.opsForHash().delete("sse_connections", emitterId);
    }

}
