package com.fishbutcher.fskotlin.member

import org.springframework.stereotype.Service

@Service
class MemberService {
    val memberRepository: MemberRepository

    constructor(memberRepository: MemberRepository) {
        this.memberRepository = memberRepository
    }

    fun createMember(request: MemberController.CreateMemberRequest): Member {
        val newMember = Member(request.name, request.password)
        return memberRepository.save(newMember)
    }
}