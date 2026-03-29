package com.example.demo.components

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (userRepository.findByUsername("user").isEmpty) {
            val defaultUser = User(
                username = "user",
                passwordHash = passwordEncoder.encode("password") ?: "",
                role = "ROLE_USER"
            )
            userRepository.save(defaultUser)
        }
    }
}
