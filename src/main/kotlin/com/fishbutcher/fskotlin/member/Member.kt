package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.visit.Visit
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_member")
class Member {
    constructor(firstName: String, lastName: String, passwordDigest: String) {
        this.memberName = MemberName(firstName, lastName);
        this.passwordDigest = passwordDigest
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_member_seq")
    var id: Long? = null

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "firstName", column = Column(name = "first_name")),
        AttributeOverride(name = "lastName", column = Column(name = "last_name"))
    )
    var memberName: MemberName?

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

