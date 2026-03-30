package com.example.demo.controller

import com.example.demo.model.CreateGameRequest
import com.example.demo.model.Game
import com.example.demo.model.Team
import com.example.demo.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/games")
class GameController(
    private val gameService: GameService
) {

    @PostMapping
    fun createGame(
        principal: Principal,
        @RequestBody(required = false) request: CreateGameRequest?
    ): ResponseEntity<Game> {
        val team = request?.team ?: Team.DETECTIVE
        val game = gameService.createGame(principal.name, team)
        return ResponseEntity.ok(game)
    }
}
