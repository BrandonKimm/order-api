package com.brandon.service;

import com.brandon.domain.Member;
import com.brandon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
