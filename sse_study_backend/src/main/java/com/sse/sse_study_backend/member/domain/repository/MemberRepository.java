package com.sse.sse_study_backend.member.domain.repository;

import com.sse.sse_study_backend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    
}
