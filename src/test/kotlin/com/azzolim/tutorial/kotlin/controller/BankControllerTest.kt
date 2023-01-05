package com.azzolim.tutorial.kotlin.controller

import com.azzolim.tutorial.kotlin.model.*
import com.fasterxml.jackson.databind.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.context.*
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
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
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMvc.get("$resource/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
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

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        
        @Test
        fun `should update an existing bank`() {
            val updatedBank = Bank("1234", 1.0, 1)

            val performPatch = mockMvc.patch(resource) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$resource/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }
        
        @Test
        fun `should return not found if no bank with given accountNo exists`() {
            val bank = Bank("does_not_exist", 1.0, 1)

            val performPatch = mockMvc.patch(resource) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            performPatch
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }

    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNo}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        
        @Test
        @DirtiesContext
        fun `should delete the bank with given account number`() {
            val accountNo = "1234"

            mockMvc.delete("$resource/$accountNo")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$resource/$accountNo")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
        
        @Test
        fun `should return not found if no bank with given account number exists`() {
            val accountNo = "does_not_exist"

            mockMvc.delete("$resource/$accountNo")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
        
    }
}