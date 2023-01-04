package com.azzolim.tutorial.kotlin.service

import com.azzolim.tutorial.kotlin.datasource.BankDataSource
import com.azzolim.tutorial.kotlin.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
}