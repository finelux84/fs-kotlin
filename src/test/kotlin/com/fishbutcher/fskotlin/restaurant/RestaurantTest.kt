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
    fun removeMenu() {
        var restaurant = Restaurant.of("스시 코호시", "성남시", "판교 테크노밸리")
        val newMenu = Menu(restaurant, "디너 오마카세", 100000)
        restaurant.addMenu(newMenu)
        restaurantRepository.save(restaurant)

        val restaurantSelectedOptional = restaurantRepository.findByName("스시 코호시")
        assertTrue(restaurantSelectedOptional.isPresent)
        val restaurantSelected = restaurantSelectedOptional.get()

        assertNotNull(restaurantSelected.menus)
        assertNotNull(restaurantSelected.id)
        assertNotNull(restaurantSelected.name)
        assertNotNull(restaurantSelected.city)
        assertNotNull(restaurantSelected.address)

        val menus = restaurantSelected.menus
        assertTrue(menus!!.isNotEmpty())
        assertTrue(menus!!.size == 1)

        val firstMenu = menus!!.first()
        assertNotNull(firstMenu.id)
        assertNotNull(firstMenu.name)
        assertNotNull(firstMenu.price)
        restaurant.removeMenu(firstMenu.id!!)
        entityManager.flush()
        entityManager.clear()

        // dirty check
        val restaurantSelectedOptional2 = restaurantRepository.findById(restaurant.id!!)
        val restaurantSelected2 = restaurantSelectedOptional2.get()
        assertTrue(restaurantSelected2.menus!!.size == 0)
    }
}