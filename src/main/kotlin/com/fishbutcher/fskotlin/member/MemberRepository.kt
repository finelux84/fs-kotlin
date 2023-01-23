package com.fishbutcher.fskotlin.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long>{
    fun findByName(name: String): Optional<Member>
}