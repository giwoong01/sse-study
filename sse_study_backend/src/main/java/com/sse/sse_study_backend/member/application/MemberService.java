package com.sse.sse_study_backend.member.application;

import com.sse.sse_study_backend.global.jwt.TokenProvider;
import com.sse.sse_study_backend.global.jwt.dto.TokenDto;
import com.sse.sse_study_backend.member.api.dto.request.MemberLoginReqDto;
import com.sse.sse_study_backend.member.api.dto.request.MemberSaveReqDto;
import com.sse.sse_study_backend.member.api.dto.response.MemberInfoResDto;
import com.sse.sse_study_backend.member.api.dto.response.MemberLoginResDto;
import com.sse.sse_study_backend.member.api.dto.response.MembersInfoResDto;
import com.sse.sse_study_backend.member.domain.Member;
import com.sse.sse_study_backend.member.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long save(MemberSaveReqDto memberSaveReqDto) {
        Member member = memberRepository.save(
                Member.builder()
                        .name(memberSaveReqDto.name())
                        .pwd(memberSaveReqDto.pwd())
                        .build()
        );

        return member.getId();
    }

    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByName(memberLoginReqDto.name())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 사용자입니다."));

        if (!member.getPwd().equals(memberLoginReqDto.pwd())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateToken(String.valueOf(member.getId()));

        return MemberLoginResDto.from(tokenDto.accessToken());
    }

    public MemberInfoResDto info(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 사용자입니다."));

        return MemberInfoResDto.from(member.getId(), member.getName());
    }

    public MembersInfoResDto findAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberInfoResDto> memberInfoResDtos = members.stream()
                .map(m -> (
                        MemberInfoResDto.from(m.getId(), m.getName())
                )).toList();

        return MembersInfoResDto.from(memberInfoResDtos);
    }

}
