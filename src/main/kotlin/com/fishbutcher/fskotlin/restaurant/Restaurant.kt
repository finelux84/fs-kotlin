package com.fishbutcher.fskotlin.restaurant

import java.time.LocalDateTime
import java.util.function.Predicate
import javax.persistence.*

@Entity
@Table(name = "tbl_restaurant")
class Restaurant(
    @Column
    var name: String,
    @Column
    var city: String,
    @Column
    var address: String
) {
    companion object {
        fun of(name: String, city: String, address: String): Restaurant {
            return Restaurant(name, city, address)
        }
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var deletedAt: LocalDateTime? = null

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE])
    var menus: MutableList<Menu>? = ArrayList()

    fun addMenu(newMenu: Menu) {
        this.menus!!.add(newMenu)
        newMenu.restaurant = this
    }

    fun removeMenu(menuId: Long) {
        if (menus.isNullOrEmpty()) {
            throw EntityNotFoundException("menus is null or empty!")
        }
        for (menu in menus!!) {
            if (menu.id == menuId) {
                menu.deletedAt = LocalDateTime.now() // soft-deleted
                return
            }
        }
    }
}