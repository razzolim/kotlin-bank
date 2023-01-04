package com.azzolim.tutorial.kotlin.controller

import com.azzolim.tutorial.kotlin.model.*
import com.azzolim.tutorial.kotlin.service.*
import org.springframework.http.*
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> = ResponseEntity(e.message, NOT_FOUND)

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNo}")
    fun bank(@PathVariable accountNo: String) = service.getBank(accountNo)

}