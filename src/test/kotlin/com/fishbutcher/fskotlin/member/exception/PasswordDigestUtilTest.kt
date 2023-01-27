package com.fishbutcher.fskotlin.member.exception

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PasswordDigestUtilTest {
    @Test
    @DisplayName("평문을 sha-256을 이용해서 digest 생성")
    fun testGetHashedPlainText() {
        val hash = PasswordDigestUtil.hash("123456")
        println(hash)

        val hash2 = PasswordDigestUtil.hash("123456789A")
        print(hash2)
    }
}
