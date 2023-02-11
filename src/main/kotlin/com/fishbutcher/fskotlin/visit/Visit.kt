package com.fishbutcher.fskotlin.visit

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_visit")
class Visit(
    @Column
    var memberId: Long,
    @Column
    var restaurantId: Long,

    @ElementCollection
    @CollectionTable(name = "tbl_order", joinColumns = [JoinColumn(name = "visit_id")])
    @OrderColumn(name = "visit_order_idx")
    var orders: MutableList<Order>? = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_visit_seq")
    var visitId: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    fun addOrder(order: Order) {
        this.orders!!.add(order)
    }
}