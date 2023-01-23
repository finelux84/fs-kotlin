package com.fishbutcher.fskotlin.review

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_review")
class Review(
    @Column
    var visitId: Long,

    @Column
    var comment: String,

    @Column
    var rating: Int

) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var deletedAt: LocalDateTime? = null


    companion object {
        fun of(visitId: Long, comment: String, rating: Int): Review {
            return Review(visitId, comment, rating)
        }
    }

}