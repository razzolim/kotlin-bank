package com.azzolim.tutorial.kotlin.datasource.network

import com.azzolim.tutorial.kotlin.datasource.BankDataSource
import com.azzolim.tutorial.kotlin.datasource.network.dto.BankList
import com.azzolim.tutorial.kotlin.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> = restTemplate.getForEntity("54.193.31.159/banks", BankList::class.java)
        return response.body?.results ?: throw IOException("Could not fetch banks from the network")
    }

    override fun retrieveBank(accountNo: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(newBank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNo: String) {
        TODO("Not yet implemented")
    }
}