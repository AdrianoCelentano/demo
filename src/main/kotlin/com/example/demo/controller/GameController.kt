package com.example.demo.controller

import com.example.demo.model.ActivateTowerRequestDto
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
import org.springframework.web.bind.annotation.DeleteMapping
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

    /** DELETE /api/game/{id} — delete a game by ID */
    @DeleteMapping("/game/{id}")
    fun deleteGame(@PathVariable id: String): ResponseEntity<Unit> {
        return try {
            gameService.deleteGame(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    /** POST /api/game/activate-tower — activate a tower in a game */
    @PostMapping("/game/activate-tower")
    fun activateTower(@RequestBody request: ActivateTowerRequestDto): ResponseEntity<Game> {
        return try {
            ResponseEntity.ok(gameService.activateTower(request.gameId, request.towerIndex))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}
