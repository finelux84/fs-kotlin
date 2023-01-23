package com.fishbutcher.fskotlin.member

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest (
    @Autowired memberService: MemberService
){
    fun createMemberTest() {

    }
}