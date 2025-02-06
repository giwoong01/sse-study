package com.sse.sse_study_backend.notification.application;

import com.sse.sse_study_backend.member.domain.Member;
import com.sse.sse_study_backend.notification.domain.repository.EmitterRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseEmitterManager {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60 * 24;

    private final EmitterRepository emitterRepository;

    public SseEmitter connect(final Long memberId, String lastEventId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> {
            log.info("비동기 요청 완료");
            emitterRepository.deleteById(emitterId);
        });

        emitter.onTimeout(() -> {
            log.info("시간 초과");
            emitter.complete();
            emitterRepository.deleteById(emitterId);
        });

        emitter.onError((e) -> {
            log.error("에러 발생", e);
            emitterRepository.deleteById(emitterId);
        });

        sendToClient(emitter, emitterId, "이벤트 스트림 생성 memberId: " + memberId);

        return emitter;
    }

    public void send(Member targetMember, String message) {
        Map<String, SseEmitter> sseEmitters = emitterRepository
                .findAllEmitterStartWithByMemberId(String.valueOf(targetMember.getId()));

        sseEmitters.forEach(
                (key, emitter) -> {
                    sendToClient(emitter, key, message);
                }
        );
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);

            try {
                throw new BadRequestException("전송 실패");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
