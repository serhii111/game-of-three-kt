package com.game.demo.service

import com.game.demo.kafka.producer.PlayerProducer
import com.game.demo.kafka.producer.WinnerProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayerService(private val playerProducer: PlayerProducer, private val winnerProducer: WinnerProducer) {

    var manualInput = false

    @Value("\${player.current}")
    private val currentPlayer: String? = null

    @Value("\${player.opponent}")
    private val opponentPlayer: String? = null

    fun startGame() {
        println("$opponentPlayer wants to play, type Yes to continue!")
        val scanner = Scanner(System.`in`)
        var input = scanner.nextLine()

        if ("Yes".equals(input, ignoreCase = true)) {
            println("To continue with manual input type M and press Enter, for automatic play press Enter.")
            input = scanner.nextLine()
            if ("M".equals(input, ignoreCase = true)) {
                manualInput = true
            }

            val randomNumber = generateRandomNumber()
            println("$currentPlayer starts the game with number: $randomNumber")
            sendNumberToKafka(randomNumber)
        }
    }

    fun generateRandomNumber(): Int {
        return Random().nextInt(100) + 1
    }

    fun processNumber(message: String) {
        val number = message.toInt()
        println("$currentPlayer received number: $number")
        if (manualInput) {
            processNumberManually(number)
        } else {
            processNumberAutomatically(number)
        }
    }

    fun processNumberAutomatically(number: Int) {
        val modifiedNumber = modifyNumberToBeDivisibleBy3Automatically(number)
        processDividedNumber(modifiedNumber)
    }

    fun modifyNumberToBeDivisibleBy3Automatically(number: Int): Int {
        if (number % 3 == 0) {
            println("$currentPlayer added: 0")
            return number
        } else if ((number + 1) % 3 == 0) {
            println("$currentPlayer added: +1")
            return number + 1
        } else {
            println("$currentPlayer added: -1")
            return number - 1
        }
    }

    fun processNumberManually(number: Int) {
        val modifiedNumber = modifyNumberToBeDivisibleBy3Manually(number)
        processDividedNumber(modifiedNumber)
    }

    fun processDividedNumber(modifiedNumber: Int) {
        val dividedNumber = modifiedNumber / 3
        if (dividedNumber == 1) {
            val winnerMessage = "$currentPlayer wins the game!"
            println(winnerMessage)
            winnerProducer.send(winnerMessage)
            manualInput = false
            return
        }
        sendNumberToKafka(dividedNumber)
    }

    fun modifyNumberToBeDivisibleBy3Manually(number: Int): Int {
        val scanner = Scanner(System.`in`)
        var input: Int

        while (true) {
            println("Please type 1, -1 or 0 to get a number divided by 3")
            try {
                input = scanner.next().toInt()

                if (input == 0 && number % 3 == 0) {
                    println("$currentPlayer added: 0")
                    return number + input
                } else if (input == 1 && (number + 1) % 3 == 0) {
                    println("$currentPlayer added: +1")
                    return number + input
                } else if (input == -1 && (number - 1) % 3 == 0) {
                    println("$currentPlayer added: -1")
                    return number + input
                } else {
                    println((number + input).toString() + " could not be divided by 3!")
                }
            } catch (numberFormatException: NumberFormatException) {
                println("Please enter only 1, -1 or 0!!!")
            }
        }
    }

    fun sendNumberToKafka(number: Int) {
        println("$currentPlayer sent number: $number")
        playerProducer.send(number.toString())
    }

    fun printWinner(message: String?) {
        println(message)
        manualInput = false
    }
}