package com.fishbutcher.fskotlin.visit

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_order")
class Order(
    @ManyToOne
    @JoinColumn(name = "order_id")
    var visit: Visit,
    @Column
    var menuId: Long
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()
}