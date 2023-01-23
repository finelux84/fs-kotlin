package com.fishbutcher.fskotlin.member

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberRepositoryTest(
    @Autowired val memberRepository: MemberRepository
) {
    @Test
    @DisplayName("회원 저장이 정상적으로 동작해야 한다")
    fun `회원 저장`() {
        // given
        val account = Member("my name", )

        // when
        memberRepository.save(account);
        val selectedAccountOptional = memberRepository.findByName(account.name)
        val selectedAccount = selectedAccountOptional.get();
        // then
        assertEquals(selectedAccount.id, account.id);
        assertEquals(selectedAccount.name, account.name);

        assertNotNull(selectedAccount.createdAt);
        assertNotNull(selectedAccount.updatedAt);
        assertNull(selectedAccount.deletedAt);
    }
}