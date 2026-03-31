package com.example.demo.controller

import com.example.demo.model.CreateGameRequest
import com.example.demo.model.Game
import com.example.demo.model.JoinGameRequest
import com.example.demo.model.Team
import com.example.demo.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping("/api")
class GameController(
    private val gameService: GameService
) {

    /** POST /api/games — create a new game */
    @PostMapping("/games")
    fun createGame(
//        principal: Principal,
    ): ResponseEntity<Game> {
        val game = gameService.createGame(Team.DETECTIVE)
//        val game = gameService.createGame(principal.name, team)
        return ResponseEntity.ok(game)
    }

    /** GET /api/games — list all open games (exactly 1 player, waiting for opponent) */
    @GetMapping("/games")
    fun loadGames(): ResponseEntity<List<Game>> {
        return ResponseEntity.ok(gameService.findOpenGames())
    }

    /** GET /api/game/{id} — fetch a single game by ID */
    @GetMapping("/game/{id}")
    fun findGameById(@PathVariable id: String): ResponseEntity<Game> {
        return try {
            ResponseEntity.ok(gameService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    /** POST /api/game/join — join an existing open game */
    @PostMapping("/game/join")
    fun joinGame(
//        principal: Principal,
        @RequestBody request: JoinGameRequest
    ): ResponseEntity<Game> {
        return try {
            ResponseEntity.ok(gameService.joinGame(UUID.randomUUID().toString(), request))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}
