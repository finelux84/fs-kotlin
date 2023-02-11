package com.fishbutcher.fskotlin.review

import com.fishbutcher.fskotlin.member.Member
import com.fishbutcher.fskotlin.member.MemberRepository
import com.fishbutcher.fskotlin.restaurant.Menu
import com.fishbutcher.fskotlin.restaurant.Restaurant
import com.fishbutcher.fskotlin.restaurant.RestaurantRepository
import com.fishbutcher.fskotlin.visit.Order
import com.fishbutcher.fskotlin.visit.Visit
import com.fishbutcher.fskotlin.visit.VisitRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.awt.SystemColor.menu

@SpringBootTest
@Transactional
class ReviewRepositoryTest(
    @Autowired val visitRepository: VisitRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val reviewRepository: ReviewRepository,
) {
    @Test
    @DisplayName("리뷰 저장 테스트")
    fun `리뷰 저장`() {
        // given
        var member = createMember()
        var restaurant = createRestaurant()
        var visit = createVisit(member, restaurant)

        // when
        val visitSelectedOptional = visitRepository.findById(visit.visitId!!)
        val review = Review.of(visit.visitId!!, "good", 5)
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

    private fun createVisit(
        member: Member,
        restaurant: Restaurant
    ): Visit {
        var order = Order(0) // 디너 오마카세
        val orders = mutableListOf<Order>(order)
        var visit = Visit(member.id!!, restaurant.id!!, orders)
        visitRepository.save(visit)
        return visit
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