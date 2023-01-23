package com.fishbutcher.fskotlin.visit

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_visit")
class Visit(
    @Column
    var memberId: Long,
    @Column
    var restaurantId: Long
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @OneToMany(mappedBy = "visit", fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    var orders: MutableList<Order>? = ArrayList()

    fun addOrder(order: Order) {
        this.orders!!.add(order)
        order.visit = this
    }
}