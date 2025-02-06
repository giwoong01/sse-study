package com.sse.sse_study_backend.member.api.dto.response;

import lombok.Builder;

@Builder
public record MemberLoginResDto(
        String accessToken
) {
    public static MemberLoginResDto from(String accessToken) {
        return MemberLoginResDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
