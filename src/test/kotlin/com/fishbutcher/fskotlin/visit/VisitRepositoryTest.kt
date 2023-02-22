package com.fishbutcher.fskotlin.visit

import com.fishbutcher.fskotlin.member.Member
import com.fishbutcher.fskotlin.member.MemberRepository
import com.fishbutcher.fskotlin.restaurant.Menu
import com.fishbutcher.fskotlin.restaurant.Restaurant
import com.fishbutcher.fskotlin.restaurant.RestaurantRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class VisitRepositoryTest(
    @Autowired val visitRepository: VisitRepository,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: EntityManager
) {
    @Test
    @DisplayName("특정 회원의 방문 목록을 조회")
    fun testCreateVisit() {
        // given
        var member = createMember()
        var restaurant = createRestaurant()
        // when
        var visit = createVisit(member, restaurant)
        visitRepository.save(visit)

        entityManager.flush()
        entityManager.clear()

        // then
        // Visit Entity가 검색되어야 하고,

        val visitOptional = visitRepository.findById(visit.id!!)
        assertTrue(visitOptional.isPresent)
        val visitSelected = visitOptional.get()

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
        assertNotNull(firstOrder.menuIdx)

        val restaurantOptional = restaurantRepository.findById(visitSelected.restaurantId)
        assertTrue(restaurantOptional.isPresent)
        val restaurantSelected = restaurantOptional.get()
        assertEquals(restaurant.name, restaurantSelected.name)
        assertEquals(restaurant.city, restaurantSelected.city)
        assertEquals(restaurant.address, restaurantSelected.address)

        val menus = restaurantSelected.menus
        assertNotNull(menus)
        assertEquals(2, menus!!.size)
        val firstMenu = menus.first()

        assertEquals("디너 오마카세", firstMenu.name)
        assertEquals(100000, firstMenu.price)
    }

    private fun createVisit(
        member: Member,
        restaurant: Restaurant
    ): Visit {
        var order = Order(0) // 디너 오마카세
        val orders = mutableListOf<Order>(order)
        return Visit(member.id!!, restaurant.id!!, orders)
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

    companion object {
        fun createVisit(
            member: Member,
            restaurant: Restaurant
        ): Visit {
            var order = Order(0) // 디너 오마카세
            val orders = mutableListOf<Order>(order)
            return Visit(member.id!!, restaurant.id!!, orders)
        }
    }
}