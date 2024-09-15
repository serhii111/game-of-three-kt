package com.game.demo.kafka.consumer

import com.game.demo.service.PlayerService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class WinnerConsumer(private val playerService: PlayerService) {

    @KafkaListener(topics = ["\${kafka.topic.winner.current}"], groupId = "game-group")
    fun gameResult(message: String?) {
        playerService.printWinner(message)
    }
}