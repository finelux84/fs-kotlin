package com.fishbutcher.fskotlin.member

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController {
    private val memberService: MemberService

    constructor(memberService: MemberService) {
        this.memberService = memberService
    }

    @PostMapping("/signin")
    fun signIn(request: SignInRequest): SignInResponse {
        TODO()
    }

    @PostMapping("/signout")
    fun signOut(request: SignOutRequest): SignOutResponse {
        TODO()
    }



    class SignInRequest(
        val memberId: Long,
        val memberPassword: String
    )

    class SignInResponse(
        val accessToken: String
    )

    class SignOutRequest(
        val accessToken: String
    )

    class SignOutResponse(
        val isSignOutSuccess: Boolean

    )
}
