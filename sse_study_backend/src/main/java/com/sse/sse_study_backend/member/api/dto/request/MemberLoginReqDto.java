package com.sse.sse_study_backend.member.api.dto.request;

public record MemberLoginReqDto(
        String name,
        String pwd
) {
}
