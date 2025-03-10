package com.sse.sse_study_backend.global.discord.util;

import com.sse.sse_study_backend.global.discord.dto.DiscordWebhookMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscordWebhookUtil {

    @Value("${logging.discord.webhook-url}")
    private String discordWebhookUrl;

    public void sendDiscordMessage(String message, String memberId) {
        try {
            DiscordWebhookMessage discordMessage = DiscordWebhookMessage.of(message, memberId);
            HttpEntity<DiscordWebhookMessage> request = createHttpRequest(discordMessage);

            new RestTemplate().postForEntity(discordWebhookUrl, request, String.class);

            System.out.println("디스코드 알림 전송 완료: " + message);
        } catch (Exception e) {
            System.err.println("디스코드 메시지 전송 실패: " + e.getMessage());
        }
    }

    private HttpEntity<DiscordWebhookMessage> createHttpRequest(DiscordWebhookMessage discordMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(discordMessage, headers);
    }

}