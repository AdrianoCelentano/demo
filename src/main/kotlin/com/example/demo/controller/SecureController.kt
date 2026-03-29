package com.example.demo.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/secure")
class SecureController {

    @GetMapping("/test")
    fun testSecure(): ResponseEntity<String> {
        val auth = SecurityContextHolder.getContext().authentication
        return ResponseEntity.ok("Hello \${auth.name}, you have accessed a secure endpoint!")
    }
}
