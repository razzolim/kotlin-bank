package com.azzolim.tutorial.kotlin

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    fun helloWorld(): String = "Hello, this is a REST endpoint!"

}