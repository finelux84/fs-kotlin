package com.fishbutcher.fskotlin.restaurant

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_menu")
@Where(clause = "deleted_at IS NULL")
class Menu(
    @ManyToOne
    @JoinColumn(name = "restaurantId")
    var restaurant:  Restaurant,
    @Column
    var name: String,
    @Column
    var price: Int
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
}