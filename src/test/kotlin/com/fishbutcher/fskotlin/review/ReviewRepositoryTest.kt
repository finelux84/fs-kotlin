package com.fishbutcher.fskotlin.review

import com.fishbutcher.fskotlin.member.Member
import com.fishbutcher.fskotlin.member.MemberRepository
import com.fishbutcher.fskotlin.restaurant.Menu
import com.fishbutcher.fskotlin.restaurant.Restaurant
import com.fishbutcher.fskotlin.restaurant.RestaurantRepository
import com.fishbutcher.fskotlin.visit.Visit
import com.fishbutcher.fskotlin.visit.VisitRepository
import com.fishbutcher.fskotlin.visit.VisitRepositoryTest
import com.fishbutcher.fskotlin.visit.VisitRepositoryTest.Companion.createVisit
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ReviewRepositoryTest(
    @Autowired val visitRepository: VisitRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val reviewRepository: ReviewRepository,
    @Autowired val entityManager: EntityManager,
) {
    companion object {
        fun createReview(visit: Visit, restaurant: Restaurant): Review {
            return Review.of(visit.id!!, "맛있어요....", 5, restaurant.id!!)
        }
    }

    @Test
    @DisplayName("리뷰 저장 테스트")
    fun `리뷰 저장`() {
        // given
        var member = createMember()
        var restaurant = createRestaurant()

        var visit = VisitRepositoryTest.createVisit(member, restaurant)
        visitRepository.save(visit)

        // when
        val review = createReview(visit, restaurant)
        val savedReview = reviewRepository.save(review)

        // then
        // 리뷰가 존재해야 한다.
        val reviewOptional = reviewRepository.findById(savedReview.id!!)
        assertTrue(reviewOptional.isPresent)
        val reviewSelected = reviewOptional.get()

        assertNotNull(reviewSelected.id)
        assertNotNull(reviewSelected.visitId)
        assertNotNull(reviewSelected.comment)
        assertNotNull(reviewSelected.rating)
        assertNotNull(reviewSelected.createdAt)
        assertNotNull(reviewSelected.updatedAt)
    }

    @Test
    @DisplayName("레스토랑 ID로 리뷰 목록 조회")
    fun `레스토랑 ID로 리뷰 목록 조회`() {
        // given
        var member = createMember()
        var restaurant = createRestaurant()

        var visit1 = createVisit(member, restaurant)
        var visit2 = createVisit(member, restaurant)
        var visit3 = createVisit(member, restaurant)
        visitRepository.save(visit1)
        visitRepository.save(visit2)
        visitRepository.save(visit3)

        // when
        val review1 = Review.of(visit1.id!!, "맛있어요.#", 5, restaurant.id!!)
        val review2 = Review.of(visit2.id!!, "맛있어요..@", 5, restaurant.id!!)
        val review3 = Review.of(visit3.id!!, "맛있어요...!", 5, restaurant.id!!)

        val reviews = mutableListOf(review1, review2, review3)
        reviewRepository.saveAll(reviews)

        entityManager.flush()
        entityManager.clear()

        val reviewsSelected = reviewRepository.findAllByRestaurantId(restaurant.id!!)

        // then
        assertEquals(3, reviewsSelected.size)
    }

    private fun createRestaurant(): Restaurant {
        val menu1 = Menu("디너 오마카세", 100000)
        val menu2 = Menu("런치 오마카세", 60000)
        val menus = mutableListOf<Menu>(menu1, menu2)
        var restaurant = Restaurant.of("스시 이도", "성남시", "판교역", menus)
        restaurantRepository.save(restaurant)
        return restaurant
    }

    private fun createMember(): Member {
        var member = Member("test-member", "test-lastname", "1234")
        memberRepository.save(member)
        return member
    }


}