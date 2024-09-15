package com.game.demo.controller

import com.game.demo.service.PlayerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("game")
class GameOfThreeController(private val playerService: PlayerService) {

    @PostMapping("/start")
    fun startGame() {
        playerService.startGame()
    }
}