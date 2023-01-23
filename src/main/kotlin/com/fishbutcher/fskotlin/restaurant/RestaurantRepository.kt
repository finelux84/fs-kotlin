package com.fishbutcher.fskotlin.restaurant

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantRepository : JpaRepository<Restaurant, Long>{
    fun findByName(name: String): Optional<Restaurant>
}