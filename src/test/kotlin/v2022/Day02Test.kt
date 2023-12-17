package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val input = """
        A Y
        B X
        C Z""".trimIndent()

    @Test
    fun `should calculate total score parsing as choices`() {
        parseAsRounds(input, Round::asChoices).sumOf { it.score } shouldBe 15
    }

    @Test
    fun `should calculate total score parsing as predictions`() {
        parseAsRounds(input, Round::asPrediction).sumOf { it.score } shouldBe 12
    }

}
