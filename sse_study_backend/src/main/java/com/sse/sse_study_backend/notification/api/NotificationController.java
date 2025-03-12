package com.sse.sse_study_backend.notification.api;

import com.sse.sse_study_backend.global.annotation.AuthenticatedMemberId;
import com.sse.sse_study_backend.notification.api.dto.response.NotificationsResDto;
import com.sse.sse_study_backend.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(
            @AuthenticatedMemberId Long memberId) {
        return ResponseEntity.ok(notificationService.connect(memberId));
    }

    @PostMapping("/send/{targetMemberId}")
    public ResponseEntity<String> send(@AuthenticatedMemberId Long memberId,
                                       @PathVariable Long targetMemberId) {
        notificationService.send(memberId, targetMemberId);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/send/all")
    public ResponseEntity<String> sendAll(@AuthenticatedMemberId Long memberId) {
        notificationService.sendAll(memberId);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/notifications")
    public ResponseEntity<NotificationsResDto> getNotifications(@AuthenticatedMemberId Long memberId) {
        return ResponseEntity.ok(notificationService.getNotifications(memberId));
    }

}
