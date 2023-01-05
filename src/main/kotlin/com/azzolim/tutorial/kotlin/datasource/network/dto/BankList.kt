package com.azzolim.tutorial.kotlin.datasource.network.dto

import com.azzolim.tutorial.kotlin.model.Bank

data class BankList(
    val results: Collection<Bank>
) {
}