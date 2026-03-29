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
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.http.HttpStatus

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
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
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

    @PostMapping("/register")
    fun register(@RequestBody request: UsernamePasswordRequest): ResponseEntity<Any> {
        if (userRepository.existsByUsername(request.username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Username already exists"))
        }

        val newUser = User(
            username = request.username,
            passwordHash = passwordEncoder.encode(request.password)!!
        )
        userRepository.save(newUser)

        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val jwtToken = jwtService.generateToken(userDetails)
        
        return ResponseEntity.ok(JwtResponse(jwtToken))
    }
}
