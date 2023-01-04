package com.azzolim.tutorial.kotlin.datasource

import com.azzolim.tutorial.kotlin.model.*

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNo: String): Bank
    fun createBank(newBank: Bank): Bank
    fun updateBank(bank: Bank): Bank

}