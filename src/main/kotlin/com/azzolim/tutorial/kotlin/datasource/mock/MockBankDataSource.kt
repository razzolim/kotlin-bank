package com.azzolim.tutorial.kotlin.datasource.mock

import com.azzolim.tutorial.kotlin.datasource.BankDataSource
import com.azzolim.tutorial.kotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource  : BankDataSource {

    val banks = listOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 17.0, 1),
        Bank("5678", 0.0, 100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
}