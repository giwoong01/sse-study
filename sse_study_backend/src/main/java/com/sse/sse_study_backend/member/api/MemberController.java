package com.sse.sse_study_backend.member.api;

import com.sse.sse_study_backend.global.annotation.AuthenticatedMemberId;
import com.sse.sse_study_backend.member.api.dto.request.MemberLoginReqDto;
import com.sse.sse_study_backend.member.api.dto.request.MemberSaveReqDto;
import com.sse.sse_study_backend.member.api.dto.response.MemberInfoResDto;
import com.sse.sse_study_backend.member.api.dto.response.MemberLoginResDto;
import com.sse.sse_study_backend.member.api.dto.response.MembersInfoResDto;
import com.sse.sse_study_backend.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Long> save(@RequestBody MemberSaveReqDto memberSaveReqDto) {
        return ResponseEntity.ok(memberService.save(memberSaveReqDto));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResDto> login(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        MemberLoginResDto memberInfoResDto = memberService.login(memberLoginReqDto);

        return ResponseEntity.ok(memberInfoResDto);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResDto> info(
            @AuthenticatedMemberId Long memberId) {
        return ResponseEntity.ok(memberService.info(memberId));
    }

    @GetMapping("/members")
    public ResponseEntity<MembersInfoResDto> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

}
