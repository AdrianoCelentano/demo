package com.example.demo.controller

import com.example.demo.service.CustomUserDetailsService
import com.example.demo.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class UsernamePasswordRequest(
    val username: String,
    val password: String
)

data class JwtResponse(
    val token: String
)

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: UsernamePasswordRequest): ResponseEntity<JwtResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        
        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val jwtToken = jwtService.generateToken(userDetails)
        
        return ResponseEntity.ok(JwtResponse(jwtToken))
    }
}
