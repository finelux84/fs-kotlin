package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.member.exception.PasswordDigestUtil
import org.springframework.stereotype.Service

@Service
class MemberService {
    val memberRepository: MemberRepository

    constructor(memberRepository: MemberRepository) {
        this.memberRepository = memberRepository
    }

    fun createMember(request: MemberController.CreateMemberRequest): Member {
        val passwordDigest = PasswordDigestUtil.hash(request.password)
        val newMember = Member(request.firstName, request.lastName, passwordDigest)
        return memberRepository.save(newMember)
    }

    fun leave(memberId: Long) {

    }
}