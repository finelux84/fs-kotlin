package com.fishbutcher.fskotlin.member.exception

import java.security.MessageDigest

class PasswordDigestUtil {
    companion object {
        fun hash(plain: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedByte = messageDigest.digest(plain.toByteArray())

            val hexChars = "0123456789ABCDEF"
            val hexCharArr = CharArray(hashedByte.size * 2)
            for (i in hashedByte.indices) {
                val v = hashedByte[i].toInt() and 0xff
                hexCharArr[i * 2] = hexChars[v shr 4]
                hexCharArr[i * 2 + 1] = hexChars[v and 0xf]
            }
            return String(hexCharArr)
        }
    }
}