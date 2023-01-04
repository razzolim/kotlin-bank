package com.azzolim.tutorial.kotlin.service

import com.azzolim.tutorial.kotlin.datasource.*
import com.azzolim.tutorial.kotlin.model.*
import org.springframework.stereotype.*

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNo: String): Bank = dataSource.retrieveBank(accountNo)
}