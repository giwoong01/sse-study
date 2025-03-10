package com.sse.sse_study_backend.global.discord.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DiscordWebhookMessage(
        String content
) {
    public static DiscordWebhookMessage of(String content, String memberId) {
        String timestamp = getCurrentTime();
        String formattedMessage = formatMessage(content, memberId, timestamp);
        
        return new DiscordWebhookMessage(formattedMessage);
    }

    private static String formatMessage(String content, String memberId, String timestamp) {
        return """
                **[시스템 알림]**
                📢 메시지: `%s`
                👤 멤버 ID: `%s`
                🕒 시간: `%s`
                """.formatted(content, memberId, timestamp);
    }

    private static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}