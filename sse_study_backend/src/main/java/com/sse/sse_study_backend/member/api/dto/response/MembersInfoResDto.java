package com.sse.sse_study_backend.member.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MembersInfoResDto(
        List<MemberInfoResDto> membersInfoResDtos
) {
    public static MembersInfoResDto from(List<MemberInfoResDto> membersInfoResDtos) {
        return MembersInfoResDto.builder()
                .membersInfoResDtos(membersInfoResDtos)
                .build();
    }
}
