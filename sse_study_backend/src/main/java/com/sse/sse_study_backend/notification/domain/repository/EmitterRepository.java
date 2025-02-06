package com.sse.sse_study_backend.notification.domain.repository;

import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    void deleteById(String emitterId);

    void deleteAllEmitterStartWithId(String memberId);

}
