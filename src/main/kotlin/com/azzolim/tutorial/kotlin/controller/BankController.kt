package com.azzolim.tutorial.kotlin.controller

import com.azzolim.tutorial.kotlin.model.*
import com.azzolim.tutorial.kotlin.service.*
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import kotlin.math.log

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    companion object {
        private val logger = LoggerFactory.getLogger(BankController::class.java)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> {
        logger.error("Error occurred, details:", e)
        return ResponseEntity(e.message, NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> {
        logger.error("Error occurred, details:", e)
        return ResponseEntity(e.message, BAD_REQUEST)
    }

    @GetMapping
    fun getBanks(): Collection<Bank> {
        logger.debug("Received request to list all banks")
        val banks = service.getBanks()
        logger.trace("Total banks found={}", banks.size)
        return banks
    }

    @GetMapping("/{accountNo}")
    fun bank(@PathVariable accountNo: String): Bank {
        logger.debug("Received request to retrieve bank with accountNo={}", accountNo)
        try {
            return service.getBank(accountNo)
        } catch (e: NoSuchElementException) {
            logger.trace("Bank with accountNo={} was not found", accountNo)
            throw e
        }
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun bank(@RequestBody newBank: Bank): Bank {
        logger.debug("Received request to create bank={}", newBank)
        return service.addBank(newBank)
    }

    @PatchMapping
    fun updateBank(@RequestBody bank: Bank): Bank {
        logger.debug("Received request to update bank={}", bank)
        try {
            return service.updateBank(bank)
        } catch (e: NoSuchElementException) {
            logger.trace("Bank with accountNo={} was not found", bank.accountNumber)
            throw e
        }
    }

    @DeleteMapping("/{accountNo}")
    @ResponseStatus(NO_CONTENT)
    fun deleteBank(@PathVariable accountNo: String) {
        logger.debug("Received request to delete bank with accountNo={}", accountNo)
        try {
            service.deleteBank(accountNo)
        } catch (e: NoSuchElementException) {
            logger.trace("Bank with accountNo={} was not found", accountNo)
            throw e
        }
    }

}