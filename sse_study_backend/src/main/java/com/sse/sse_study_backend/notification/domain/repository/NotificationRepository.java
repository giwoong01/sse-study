package com.sse.sse_study_backend.notification.domain.repository;

import com.sse.sse_study_backend.member.domain.Member;
import com.sse.sse_study_backend.notification.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiver(Member receiver);
}
