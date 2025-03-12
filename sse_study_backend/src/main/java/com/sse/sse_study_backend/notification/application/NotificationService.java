package com.sse.sse_study_backend.notification.application;

import com.sse.sse_study_backend.member.domain.Member;
import com.sse.sse_study_backend.member.domain.repository.MemberRepository;
import com.sse.sse_study_backend.member.exception.MemberNotFoundException;
import com.sse.sse_study_backend.notification.api.dto.response.NotificationsResDto;
import com.sse.sse_study_backend.notification.domain.Notification;
import com.sse.sse_study_backend.notification.domain.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final SseEmitterManager sseEmitterManager;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter connect(Long memberId) {
        return sseEmitterManager.connect(memberId);
    }

    @Transactional
    public void send(Long memberId, Long targetMemberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Member targetMember = memberRepository.findById(targetMemberId).orElseThrow(MemberNotFoundException::new);

        String message = "name: " + member.getName() + "님이 알림을 보냈습니다.";

        notificationRepository.save(Notification.builder()
                .receiver(targetMember)
                .message(message)
                .build());

        sseEmitterManager.send(targetMember, message);
    }

    @Transactional
    public void sendAll(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        String message = "name: " + member.getName() + "님이 📢전체 알림을 보냈습니다.";

        sseEmitterManager.sendAll(message);
    }

    public NotificationsResDto getNotifications(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<Notification> allByReceiver = notificationRepository.findAllByReceiver(member);

        return NotificationsResDto.from(allByReceiver);
    }

}
