package com.azzolim.tutorial.kotlin.datasource.mock

import com.azzolim.tutorial.kotlin.datasource.*
import com.azzolim.tutorial.kotlin.model.*
import org.springframework.stereotype.*

@Repository
class MockBankDataSource  : BankDataSource {

    val banks = mutableListOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 17.0, 1),
        Bank("5678", 0.0, 100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNo: String): Bank = banks.firstOrNull() { it.accountNumber == accountNo } ?:
        throw NoSuchElementException("Could not find a bank with accountNumber={$accountNo}")

    override fun createBank(newBank: Bank): Bank {
        if (banks.any() { it.accountNumber == newBank.accountNumber}) {
            throw IllegalArgumentException("Bank with accountNo={${newBank.accountNumber}} already exists")
        }
        banks.add(newBank)
        return newBank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = findBankOrThrowsException(bank.accountNumber)
        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNo: String) {
        val currentBank = findBankOrThrowsException(accountNo)
        banks.remove(currentBank)
    }

    private fun findBankOrThrowsException(accountNo: String): Bank {
        return banks.firstOrNull { it.accountNumber == accountNo } ?:
        throw NoSuchElementException("Could not find bank with accountNo={${accountNo}}")
    }

}