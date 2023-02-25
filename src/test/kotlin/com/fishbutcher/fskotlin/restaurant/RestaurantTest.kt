package com.fishbutcher.fskotlin.restaurant

import org.assertj.core.api.Assertions.assertThat
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
    fun removeMenuTest() {
        // given
        val restaurant = createRestaurant()
        restaurantRepository.save(restaurant)

        // when
        val menuIndex = 0
        restaurant.removeMenu(menuIndex)
        entityManager.flush()
        entityManager.clear()

        // then
        val menusAvailable = restaurant.getMenusAvailable()
        assertTrue(menusAvailable.size == 1)

        val menusWithDeleted = restaurant.getMenusWithDeleted()
        assertTrue(menusWithDeleted.size == 2)
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

    @Test
    fun getAllMenusTest() {
        // given
        val restaurant = createRestaurant()
        // when
        restaurantRepository.save(restaurant)
        // then
        val restaurant1 = restaurantRepository.findById(restaurant.id!!).get()
        val menusWithDeleted = restaurant1.getMenusWithDeleted()
        assertThat(menusWithDeleted.size).isEqualTo(2)
    }

    @Test
    fun getAvailableMenusTest() {
        // given
        val restaurant = createRestaurant()
        // when
        restaurantRepository.save(restaurant)

        entityManager.flush()
        entityManager.clear()

        // then
        val restaurant1 = restaurantRepository.findById(restaurant.id!!).get()
        restaurant1.removeMenu(0)
        val availableMenus = restaurant1.getMenusAvailable()
        assertThat(availableMenus.size).isEqualTo(1)
    }

    companion object {
        fun createRestaurant(): Restaurant {
            val menu1 = Menu("디너 오마카세", 100000)
            val menu2 = Menu("런치 오마카세", 60000)
            val menus = mutableListOf<Menu>(menu1, menu2)
            return Restaurant.of("스시 코호시", "성남시", "판교 테크노밸리", menus)
        }
    }

}