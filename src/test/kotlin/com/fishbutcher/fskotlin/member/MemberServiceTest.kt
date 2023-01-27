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
        val memberName = "finelux32"
        val createMemberRequest = MemberController.CreateMemberRequest(memberName, plainTextPassword, plainTextPassword)
        // when
        memberService.createMember(createMemberRequest)
        // then
        val memberOptional = memberRepository.findByName(memberName)
        assertTrue(memberOptional.isPresent)
        val member = memberOptional.get()
        assertEquals(memberName, member.name)
        assertEquals(PasswordDigestUtil.hash(plainTextPassword), member.passwordDigest)
    }
}