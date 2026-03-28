package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController {

    @GetMapping("/greet")
    fun greetUser(@RequestParam(value = "name", defaultValue = "Adrian") name: String): String {
        return "Hello, $name! Your Spring Boot backend is live."
    }
}