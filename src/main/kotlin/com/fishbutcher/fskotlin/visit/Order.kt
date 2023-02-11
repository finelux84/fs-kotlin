package com.fishbutcher.fskotlin.visit

import java.time.LocalDateTime
import javax.persistence.*

@Embeddable
class Order(
    var menuIdx: Long
) {
}