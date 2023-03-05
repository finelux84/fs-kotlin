package com.fishbutcher.fskotlin.visit

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VisitRepository : JpaRepository<Visit, Long>{
    fun findByMemberId(memberId: Long)
    fun findByRestaurantId(restaurantId: Long): List<Visit>
}