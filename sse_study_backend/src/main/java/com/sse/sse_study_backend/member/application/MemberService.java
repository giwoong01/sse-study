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
import com.sse.sse_study_backend.member.exception.MemberNotFoundException;
import com.sse.sse_study_backend.member.exception.PasswordMismatchException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long save(MemberSaveReqDto memberSaveReqDto) {
        Member member = memberRepository.save(
                Member.builder()
                        .name(memberSaveReqDto.name())
                        .pwd(passwordEncoder.encode(memberSaveReqDto.pwd()))
                        .build()
        );

        return member.getId();
    }

    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByName(memberLoginReqDto.name()).orElseThrow(MemberNotFoundException::new);

        validatePassword(memberLoginReqDto.pwd(), member.getPwd());

        TokenDto tokenDto = tokenProvider.generateToken(String.valueOf(member.getId()));

        return MemberLoginResDto.from(tokenDto.accessToken());
    }

    private void validatePassword(CharSequence loginPwd, String memberPwd) {
        if (!passwordEncoder.matches(loginPwd, memberPwd)) {
            throw new PasswordMismatchException();
        }
    }

    public MemberInfoResDto info(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

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
