package com.fishbutcher.fskotlin.restaurant

import javax.persistence.*

@Embeddable
class Menu(
    var name: String,
    var price: Int
) {
}