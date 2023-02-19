package com.fishbutcher.fskotlin.review

import com.fishbutcher.fskotlin.visit.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, Long>{

}