package com.game.demo.kafka.consumer

import com.game.demo.service.PlayerService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PlayerConsumer(private val playerService: PlayerService) {

    @KafkaListener(topics = ["\${kafka.topic.listener}"], groupId = "game-group")
    fun listenFromPlayer1(message: String?) {
        playerService.processNumber(message!!)
    }
}