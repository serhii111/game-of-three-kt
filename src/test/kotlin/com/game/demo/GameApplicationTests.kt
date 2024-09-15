package com.game.demo

import com.game.demo.service.PlayerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GameApplicationTests {

	@InjectMocks
	private val playerService: PlayerService? = null

	@Test
	@DisplayName("Test case where the number is already divisible by 3")
	fun testNumberDivisibleBy3() {
		val number = 9
		val result = playerService!!.modifyNumberToBeDivisibleBy3Automatically(number)
		Assertions.assertEquals(9, result, "The number is already divisible by 3, no change should be made.")
	}

	@Test
	@DisplayName("Test case where the number needs to be incremented by 1 to be divisible by 3")
	fun testNumberNeedsIncrement() {
		val number = 11
		val result = playerService!!.modifyNumberToBeDivisibleBy3Automatically(number)
		Assertions.assertEquals(12, result, "The number should be incremented by 1 to be divisible by 3.")
	}

	@Test
	@DisplayName("Test case where the number needs to be decremented by 1 to be divisible by 3")
	fun testNumberNeedsDecrement() {
		val number = 7
		val result = playerService!!.modifyNumberToBeDivisibleBy3Automatically(number)
		Assertions.assertEquals(6, result, "The number should be decremented by 1 to be divisible by 3.")
	}

}
