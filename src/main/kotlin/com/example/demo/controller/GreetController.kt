package com.example.demo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetController {

    @GetMapping("/greet")
    fun greet(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, this is a public endpoint!")
    }
}
