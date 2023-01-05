package com.azzolim.tutorial.kotlin.service

import com.azzolim.tutorial.kotlin.datasource.BankDataSource
import com.azzolim.tutorial.kotlin.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("mock") private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNo: String): Bank = dataSource.retrieveBank(accountNo)
    fun addBank(newBank: Bank): Bank = dataSource.createBank(newBank)
    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    fun deleteBank(accountNo: String): Unit = dataSource.deleteBank(accountNo)
}