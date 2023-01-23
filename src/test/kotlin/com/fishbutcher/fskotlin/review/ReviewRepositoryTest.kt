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
        var member = Member("test-member")
        memberRepository.save(member)

        var restaurant = Restaurant.of("스시 이도", "성남시", "판교역")
        var menu = Menu(restaurant, "디너 오마카세", 250000)
        restaurant.addMenu(menu)
        restaurantRepository.save(restaurant)

        var visit = Visit(member.id!!, restaurant.id!!)
        var order = Order(visit, menu.id!!)
        visit.addOrder(order)
        visitRepository.save(visit)

        // when
        val visitSelectedOptional = visitRepository.findById(visit.id!!)

        // then
        // Visit Entity가 검색되어야 하고,
        assertTrue(visitSelectedOptional.isPresent)
        val visitSelected = visitSelectedOptional.get()

        assertNotNull(visitSelected.id)
        assertNotNull(visitSelected.memberId)
        assertNotNull(visitSelected.restaurantId)
        assertNotNull(visitSelected.createdAt)
        assertNotNull(visitSelected.updatedAt)

        // Order, Restaurant, Menu가 맵핑되어 있어야 한다.
        assertFalse(visitSelected.orders.isNullOrEmpty())

        val orders = visitSelected.orders
        assertTrue(orders!!.size == 1)

        val firstOrder = orders!!.first()
        assertNotNull(firstOrder.id)
        assertNotNull(firstOrder.visit)
        assertNotNull(firstOrder.menuId)
        assertNotNull(firstOrder.createdAt)
        assertNotNull(firstOrder.updatedAt)

        val restaurantOptional = restaurantRepository.findById(visitSelected.restaurantId)
        assertTrue(restaurantOptional.isPresent)
        val restaurantSelected = restaurantOptional.get()
        assertEquals(restaurant.name, restaurantSelected.name)
        assertEquals(restaurant.city, restaurantSelected.city)
        assertEquals(restaurant.address, restaurantSelected.address)

        val menus = restaurantSelected.menus
        assertNotNull(menus)
        assertEquals(1, menus!!.size)
        val firstMenu = menus.first()
        assertEquals(menu.id, firstMenu.id)
        assertEquals(menu.name, firstMenu.name)
        assertEquals(menu.price, firstMenu.price)
        assertEquals(menu.createdAt, firstMenu.createdAt)
        assertEquals(menu.updatedAt, firstMenu.updatedAt)

        val review = Review.of(visit.id!!, "맛있어요!!", 5)
        reviewRepository.save(review)

        val reviewSelectedOptional = reviewRepository.findById(review.id!!)
        assertTrue(reviewSelectedOptional.isPresent)
        val reviewSelected = reviewSelectedOptional.get()
        assertEquals(review.id, reviewSelected.id)
        assertEquals(review.visitId, reviewSelected.visitId)
        assertEquals(review.comment, reviewSelected.comment)
        assertEquals(review.rating, reviewSelected.rating)
    }


}