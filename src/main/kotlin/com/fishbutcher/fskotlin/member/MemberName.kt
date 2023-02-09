package com.fishbutcher.fskotlin.member

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MemberName(
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
