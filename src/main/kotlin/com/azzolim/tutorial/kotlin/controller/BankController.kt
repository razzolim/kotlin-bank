package com.azzolim.tutorial.kotlin.controller

import com.azzolim.tutorial.kotlin.model.*
import com.azzolim.tutorial.kotlin.service.*
import org.springframework.http.*
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> = ResponseEntity(e.message, NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> = ResponseEntity(e.message, BAD_REQUEST)

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNo}")
    fun bank(@PathVariable accountNo: String) = service.getBank(accountNo)

    @PostMapping
    @ResponseStatus(CREATED)
    fun bank(@RequestBody newBank: Bank) = service.addBank(newBank)

}