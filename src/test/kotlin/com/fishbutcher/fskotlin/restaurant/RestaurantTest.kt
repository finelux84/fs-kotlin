package com.fishbutcher.fskotlin.restaurant

import com.fishbutcher.fskotlin.member.MemberRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction

@Transactional
@SpringBootTest
class RestaurantTest (
    @Autowired val menuRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: EntityManager,
){

    @Test
    fun addMenuTest() {
        // given
        val restaurant = createRestaurant()
        restaurantRepository.save(restaurant)

        // when
        val newMenu = Menu("화요", 35000)
        restaurant.addMenu(newMenu)

        entityManager.flush()
        entityManager.clear()

        // then
        val restaurantOptional = restaurantRepository.findById(restaurant.id!!)
        val restaurantGot = restaurantOptional.get()
        assertTrue(restaurantGot.menus.size == 3)

    }
    @Test
    fun createRestaurantTest() {
        // given
        val restaurant = createRestaurant()
        // when
        restaurantRepository.save(restaurant)
        // then
        val restaurantSelectedOptional = restaurantRepository.findByName("스시 코호시")
        assertTrue(restaurantSelectedOptional.isPresent)
        val restaurantSelected = restaurantSelectedOptional.get()

        assertNotNull(restaurantSelected.id)
        assertNotNull(restaurantSelected.name)
        assertNotNull(restaurantSelected.city)
        assertNotNull(restaurantSelected.address)
        assertNotNull(restaurantSelected.menus)

        val menus = restaurant.menus
        assertTrue(menus!!.isNotEmpty())
        assertTrue(menus!!.size == 2)

        val firstMenu = menus!!.first()
        assertNotNull(firstMenu.name)
        assertNotNull(firstMenu.price)
        entityManager.flush()
        entityManager.clear()
    }

    private fun createRestaurant(): Restaurant {
        val menu1 = Menu("디너 오마카세", 100000)
        val menu2 = Menu("런치 오마카세", 60000)
        val menus = mutableListOf<Menu>(menu1, menu2)
        var restaurant = Restaurant.of("스시 코호시", "성남시", "판교 테크노밸리", menus)
        return restaurant
    }
}