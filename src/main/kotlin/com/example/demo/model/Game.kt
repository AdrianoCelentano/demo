package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

enum class Team {
    DETECTIVE,
    MISTER_X
}

data class LatLng(
    val latitude: Double,
    val longitude: Double
)

data class Tower(
    val isActive: Boolean = false,
    val position: LatLng
)

data class Player(
    val userId: Long,
    val team: Team,
    val position: LatLng
)

@RedisHash("Game")
data class Game(
    @Id val id: String? = null,
    val playgroundBoundaries: List<LatLng> = emptyList(),
    val players: List<Player> = emptyList(),
    val towers: List<Tower> = emptyList()
)

data class CreateGameRequest(
    val team: Team = Team.DETECTIVE
)

data class JoinGameRequest(
    val gameId: String
)

