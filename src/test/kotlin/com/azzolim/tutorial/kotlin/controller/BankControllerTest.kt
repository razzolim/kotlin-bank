package com.azzolim.tutorial.kotlin.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.http.*
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val resource: String = "/api/banks"
    
    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {

            mockMvc.get(resource)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].account_number") { value("1234")}
                }
        }
    
    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number`() {
            val accountNo = "1234"

            mockMvc.get("$resource/$accountNo")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.default_transaction_fee") { value("17") }
                }
        }

        @Test
        fun `should return not found if the account number does not exist`() {
            val accountNo = "does_not_exist"

            mockMvc.get("$resource/$accountNo")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

    }
}