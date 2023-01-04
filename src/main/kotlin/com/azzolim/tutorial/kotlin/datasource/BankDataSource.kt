package com.azzolim.tutorial.kotlin.datasource

import com.azzolim.tutorial.kotlin.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

}