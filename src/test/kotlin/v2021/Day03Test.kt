package v2021

import gg.jte.aoc.v2021.calculateEpsilonRate
import gg.jte.aoc.v2021.calculateGammaRate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day03Test {

    @Test
    fun `should calculate gamma rate`() {
        calculateGammaRate(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"
            )
        ) shouldBe 22
    }

    @Test
    fun `should calculate epsilon rate`() {
        calculateEpsilonRate(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"
            )
        ) shouldBe 9
    }

}