package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.visit.Visit
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_member")
class Member {
    constructor(name: String, passwordDigest: String) {
        this.name = name
        this.passwordDigest = passwordDigest
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var name: String

    @Column
    var passwordDigest: String

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var deletedAt: LocalDateTime? = null


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    var visits: List<Visit>? = null
}