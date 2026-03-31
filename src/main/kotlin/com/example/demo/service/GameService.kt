package com.example.demo.service

import com.example.demo.model.Game
import com.example.demo.model.JoinGameRequest
import com.example.demo.model.LatLng
import com.example.demo.model.Player
import com.example.demo.model.Team
import com.example.demo.model.Tower
import com.example.demo.model.eggensteinMap
import com.example.demo.model.eggensteinTowers
import com.example.demo.repository.GameRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.random.Random

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) {

    fun createGame(chosenTeam: Team): Game {
//        val user = userRepository.findByUsername(username)
//            .orElseThrow { IllegalArgumentException("User not found") }

        val initialPlayer = Player(
            // random Long
            userId = Random.nextLong(0, 1000),
            team = chosenTeam,
            position = LatLng(0.0, 0.0)
        )

        val game = Game(
            id = UUID.randomUUID().toString(),
            players = listOf(initialPlayer),
            towers = generateRandomTowers(),
            playgroundBoundaries = eggensteinMap
        )

        return gameRepository.save(game)
    }

    private fun generateRandomTowers(): List<Tower> {
        return eggensteinTowers.shuffled().take(3)
    }

    /** Returns all games that have exactly one player (open/waiting games). */
    fun findOpenGames(): List<Game> {
        return gameRepository.findAll()
            .filter { it.players.size == 1 }
    }

    fun findById(id: String): Game {
        return gameRepository.findById(id)
            .orElseThrow { NoSuchElementException("Game not found: $id") }
    }

    fun joinGame(username: String, request: JoinGameRequest): Game {
//        val user = userRepository.findByUsername(username)
//            .orElseThrow { IllegalArgumentException("User not found") }

        val game = gameRepository.findById(request.gameId)
            .orElseThrow { NoSuchElementException("Game not found: ${request.gameId}") }

        require(game.players.size == 1) { "Game is already full or has no players" }

        val newPlayer = Player(
            userId = Random.nextLong(0, 1000),
            team = Team.MISTER_X,
            position = LatLng(0.0, 0.0)
        )

        val updatedGame = game.copy(players = game.players + newPlayer)
        return gameRepository.save(updatedGame)
    }
}
