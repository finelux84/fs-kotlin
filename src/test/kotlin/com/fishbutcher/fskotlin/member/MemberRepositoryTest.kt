package com.fishbutcher.fskotlin.member

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired val memberRepository: MemberRepository,
    @Autowired val entityManager: EntityManager
) {
    @Test
    @DisplayName("회원 저장이 정상적으로 동작해야 한다")
    fun `회원 저장`() {
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

    companion object {
        fun createMember(): Member {
            return Member("my name", "my last name", "1234")
        }
    }
}