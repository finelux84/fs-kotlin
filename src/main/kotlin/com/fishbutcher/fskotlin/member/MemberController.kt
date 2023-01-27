package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.member.exception.PasswordConfirmationFailException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController {
    private val memberService: MemberService

    constructor(memberService: MemberService) {
        this.memberService = memberService
    }

    @PostMapping
    fun createMember(request: CreateMemberRequest): CreateMemberResponse {
        validatePassword(request)

        return try {
            val createdMember = memberService.createMember(request)
            of(createdMember)
        } catch (e: Exception) {
            CreateMemberResponse(false, null, null)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long) {
        memberService.leave(id);
    }

    private fun of(createdMember: Member): CreateMemberResponse {
        return CreateMemberResponse(true, createdMember.id!!, createdMember.name)
    }

    private fun validatePassword(request: CreateMemberRequest) {
        if (request.password != request.passwordConfirmation) {
            throw PasswordConfirmationFailException("password and password confirmation mismatched!")
        }
    }

    class CreateMemberRequest(
        val name: String,
        val password: String,
        val passwordConfirmation: String
    )

    class CreateMemberResponse(
        val success: Boolean, val memberId: Long?, val memberName: String?)
}
