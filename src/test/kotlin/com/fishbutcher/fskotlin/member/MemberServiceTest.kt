package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.member.exception.PasswordDigestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest (
    @Autowired val memberService: MemberService,
    @Autowired val memberRepository: MemberRepository
){
    @Test
    @DisplayName("회원 생성 서비스 테스트")
    fun createMemberTest() {
        // given
        val plainTextPassword = "12345"
        val memberFirstName = "finelux"
        val memberLastName = "32"
        val memberName = MemberName(memberFirstName, memberLastName)

        val createMemberRequest = MemberController.CreateMemberRequest(memberFirstName, memberLastName, plainTextPassword, plainTextPassword)
        // when
        memberService.createMember(createMemberRequest)
        // then
        val memberOptional = memberRepository.findByMemberName(memberName)
        assertTrue(memberOptional.isPresent)
        val member = memberOptional.get()
        assertEquals(memberName.getFullName(), member.memberName!!.getFullName())
        assertEquals(PasswordDigestUtil.hash(plainTextPassword), member.passwordDigest)
    }
}