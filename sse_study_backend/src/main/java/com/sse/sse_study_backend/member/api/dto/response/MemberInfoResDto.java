package com.sse.sse_study_backend.member.api.dto.response;

import lombok.Builder;

@Builder
public record MemberInfoResDto(
        Long memberId,
        String name
) {
    public static MemberInfoResDto from(Long memberId, String name) {
        return MemberInfoResDto.builder()
                .memberId(memberId)
                .name(name)
                .build();
    }
}
