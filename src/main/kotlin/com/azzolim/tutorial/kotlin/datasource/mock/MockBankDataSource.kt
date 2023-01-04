package com.azzolim.tutorial.kotlin.datasource.mock

import com.azzolim.tutorial.kotlin.datasource.*
import com.azzolim.tutorial.kotlin.model.*
import org.springframework.stereotype.*

@Repository
class MockBankDataSource  : BankDataSource {

    val banks = listOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 17.0, 1),
        Bank("5678", 0.0, 100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNo: String): Bank = banks.firstOrNull() { it.accountNumber == accountNo } ?:
        throw NoSuchElementException("Could not find a bank with accountNumber={$accountNo}")

}