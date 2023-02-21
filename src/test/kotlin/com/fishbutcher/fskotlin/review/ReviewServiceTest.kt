package com.fishbutcher.fskotlin.review

import com.fishbutcher.fskotlin.member.MemberRepository
import com.fishbutcher.fskotlin.member.MemberRepositoryTest
import com.fishbutcher.fskotlin.restaurant.RestaurantRepository
import com.fishbutcher.fskotlin.restaurant.RestaurantTest
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ReviewServiceTest(
    @Autowired val memberRepository: MemberRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: EntityManager) {
    @Test
    fun writeReview() {
        // given
        val restaurant = RestaurantTest.createRestaurant()
        restaurantRepository.save(restaurant)

        val member = MemberRepositoryTest.createMember()
        memberRepository.save(member)

        // when
        // then
    }
}