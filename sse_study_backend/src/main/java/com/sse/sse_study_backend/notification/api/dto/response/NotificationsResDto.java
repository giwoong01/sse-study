package com.sse.sse_study_backend.notification.api.dto.response;

import com.sse.sse_study_backend.notification.domain.Notification;
import java.util.List;
import lombok.Builder;

@Builder
public record NotificationsResDto(
        List<NotificationResDto> notifications
) {
    public static NotificationsResDto from(List<Notification> notifications) {
        return NotificationsResDto.builder()
                .notifications(notifications.stream()
                        .map((notification) -> NotificationResDto.from(notification.getMessage()))
                        .toList())
                .build();
    }

    @Builder
    private record NotificationResDto(
            String message
    ) {
        public static NotificationResDto from(String message) {
            return new NotificationResDto(message);
        }
    }
    
}
