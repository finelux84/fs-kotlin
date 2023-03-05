package com.fishbutcher.fskotlin.member

import com.fishbutcher.fskotlin.member.MemberRepositoryTest.Companion.createMember
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class MemberRepositoryKoTest(
    val memberRepository: MemberRepository,
    val entityManager: EntityManager
): DescribeSpec() {
    init {
        describe("MemberRepository test") {
            context("member save") {
                it("id가 자동 생성") {
                    // given
                    val account = createMember()

                    // when
                    memberRepository.save(account);

                    entityManager.flush()
                    entityManager.clear()

                    val selectedAccountOptional = memberRepository.findByMemberName(account.memberName!!)
                    val selectedAccount = selectedAccountOptional.get();
                    // then
                    assertEquals(selectedAccount.id, account.id);
                    assertEquals(selectedAccount.memberName!!.getFullName(), account.memberName!!.getFullName());

                    assertNotNull(selectedAccount.createdAt);
                    assertNotNull(selectedAccount.updatedAt);
                    assertNull(selectedAccount.deletedAt);

                }
            }
        }
    }
}