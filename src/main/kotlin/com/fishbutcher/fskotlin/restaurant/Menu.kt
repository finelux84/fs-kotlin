package com.fishbutcher.fskotlin.restaurant

import java.time.LocalDateTime
import javax.persistence.*

@Embeddable
class Menu(
    var name: String,
    var price: Int,
    var deletedAt: LocalDateTime? = null
) {
}