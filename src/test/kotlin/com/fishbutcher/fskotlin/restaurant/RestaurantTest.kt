package com.fishbutcher.fskotlin.restaurant

import com.fishbutcher.fskotlin.member.MemberRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
class RestaurantTest (
    @Autowired val menuRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: EntityManager,
){

    @Test
    fun createRestaurant() {
        val menu1 = Menu("디너 오마카세", 100000)
        val menu2 = Menu("런치 오마카세", 60000)
        val menus = mutableListOf<Menu>(menu1, menu2)
        var restaurant = Restaurant.of("스시 코호시", "성남시", "판교 테크노밸리", menus)
        restaurantRepository.save(restaurant)

        val restaurantSelectedOptional = restaurantRepository.findByName("스시 코호시")
        assertTrue(restaurantSelectedOptional.isPresent)
        val restaurantSelected = restaurantSelectedOptional.get()

        assertNotNull(restaurantSelected.id)
        assertNotNull(restaurantSelected.name)
        assertNotNull(restaurantSelected.city)
        assertNotNull(restaurantSelected.address)
        assertNotNull(restaurantSelected.menus)

        assertTrue(menus!!.isNotEmpty())
        assertTrue(menus!!.size == 2)

        val firstMenu = menus!!.first()
        assertNotNull(firstMenu.name)
        assertNotNull(firstMenu.price)
        entityManager.flush()
        entityManager.clear()
    }
}