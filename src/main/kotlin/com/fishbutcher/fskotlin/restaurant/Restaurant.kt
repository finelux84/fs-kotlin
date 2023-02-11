package com.fishbutcher.fskotlin.restaurant

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_restaurant")
class Restaurant(
    @Column
    var name: String,
    @Column
    var city: String,
    @Column
    var address: String,
    @ElementCollection
    @CollectionTable(name = "tbl_menu", joinColumns = [JoinColumn(name = "restaurant_id")])
    @OrderColumn(name = "restaurant_menu_idx")
    var menus: MutableList<Menu>
) {
    companion object {
        fun of(name: String, city: String, address: String, menus: MutableList<Menu>): Restaurant {
            return Restaurant(name, city, address, menus)
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_restaurant_seq")
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var deletedAt: LocalDateTime? = null

    fun addMenu(newMenu: Menu) {
        this.menus!!.add(newMenu)
    }

}