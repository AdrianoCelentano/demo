package com.example.demo.service

import com.example.demo.model.Game
import com.example.demo.model.LatLng
import com.example.demo.model.Player
import com.example.demo.model.Team
import com.example.demo.repository.GameRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) {
    fun createGame(username: String, chosenTeam: Team): Game {
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalArgumentException("User not found") }

        val initialPlayer = Player(
            userId = user.id,
            team = chosenTeam,
            position = LatLng(0.0, 0.0) // Provide a default or initial position
        )

        val game = Game(
            players = listOf(initialPlayer),
            towers = emptyList() // You can add logic later to initialize towers
        )
        
        return gameRepository.save(game)
    }
}
