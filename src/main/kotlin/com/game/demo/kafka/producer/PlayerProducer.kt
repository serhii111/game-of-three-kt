package com.game.demo.kafka.producer

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PlayerProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    @Value("\${kafka.topic.producer}")
    private val topic: String? = null

    fun send(message: String) {
        kafkaTemplate.send(topic!!, message)
    }
}