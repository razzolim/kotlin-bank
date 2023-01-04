package com.azzolim.tutorial.kotlin.controller

import com.azzolim.tutorial.kotlin.model.*
import com.fasterxml.jackson.databind.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.http.*
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(val mockMvc: MockMvc, val objectMapper: ObjectMapper) {

    val resource: String = "/api/banks"
    
    @Nested
    @DisplayName("GET /api/banks")
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
    @DisplayName("GET /api/banks/accountNo")
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
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new bank`() {
            val newBank = Bank("acc123", 31.415, 2)

            val performPost = mockMvc.post(resource) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.account_number") { value("acc123") }
                    jsonPath("$.trust") { value(31.415) }
                    jsonPath("$.default_transaction_fee") { value(2) }
                }
        }
        
        @Test
        fun `should return bad request if bank with given account # already exists`() {
            val invalidBank = Bank("1234", 1.0, 1)

            val performPost = mockMvc.post(resource) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    
    }
}