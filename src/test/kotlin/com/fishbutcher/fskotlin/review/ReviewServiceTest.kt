package com.fishbutcher.fskotlin.review

import com.fishbutcher.fskotlin.member.MemberRepository
import com.fishbutcher.fskotlin.member.MemberRepositoryTest
import com.fishbutcher.fskotlin.restaurant.RestaurantRepository
import com.fishbutcher.fskotlin.restaurant.RestaurantTest
import com.fishbutcher.fskotlin.visit.VisitRepository
import com.fishbutcher.fskotlin.visit.VisitRepositoryTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ReviewServiceTest(
    @Autowired val reviewService: ReviewService,
    @Autowired val reviewRepository: ReviewRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val visitRepository: VisitRepository,
    @Autowired val entityManager: EntityManager) {
    @Test
    fun writeReview() {
        // given
        val restaurant = RestaurantTest.createRestaurant()
        restaurantRepository.save(restaurant)

        val member = MemberRepositoryTest.createMember()
        memberRepository.save(member)

        var visit = VisitRepositoryTest.createVisit(member, restaurant)
        visitRepository.save(visit)

        // when
        val reviewId = reviewService.writeReview(restaurant.id!!, visit.id!!, "맛있다!", 5)

        // then
        entityManager.flush()
        entityManager.clear()

        val reviewOptional = reviewRepository.findById(reviewId)
        assertTrue(reviewOptional.isPresent)

        val review = reviewOptional.get()
        assertEquals(restaurant.id!!, review.restaurantId)
        assertEquals(visit.id!!, review.visitId)
        assertEquals("맛있다!", review.comment)
        assertEquals(5, review.rating)
    }
}