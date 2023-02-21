package com.fishbutcher.fskotlin.review

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_review")
@SequenceGenerator(
    name = "review_seq_generator",
    sequenceName = "review_sequence",
    initialValue = 1, allocationSize = 1
)
class Review(
    @Column
    var visitId: Long,

    @Column
    var comment: String,

    @Column
    var rating: Int,

    @Column
    var restaurantId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var deletedAt: LocalDateTime? = null

    companion object {
        fun of(visitId: Long, comment: String, rating: Int, restaurantId: Long): Review {
            return Review(visitId, comment, rating, restaurantId)
        }
    }
}