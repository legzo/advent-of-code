package gg.jte.aoc.v2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val input = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
        """.trimIndent()

    @Test
    fun `should get next sequence of diffs`() {
        "0 3 6 9 12 15".sequenceOfDiffs().toList() shouldBe listOf(
            listOf(0, 3, 6, 9, 12, 15),
            listOf(3, 3, 3, 3, 3)
        )

        "10 13 16 21 30 45".sequenceOfDiffs().toList() shouldBe listOf(
            listOf(10, 13, 16, 21, 30, 45),
            listOf(3, 3, 5, 9, 15),
            listOf(0, 2, 4, 6),
            listOf(2, 2, 2),
        )
    }

    @Test
    fun `should get next value`() {
        "0 3 6 9 12 15".getNextValue() shouldBe 18
        "1 3 6 10 15 21".getNextValue() shouldBe 28
        "10 13 16 21 30 45".getNextValue() shouldBe 68
    }

    @Test
    fun `should get previous value`() {
        "0 3 6 9 12 15".getPreviousValue() shouldBe -3
        "1 3 6 10 15 21".getPreviousValue() shouldBe 0
        "10 13 16 21 30 45".getPreviousValue() shouldBe 5
    }

    @Test
    fun `should get sum of extrapolated values`() {
        input.sumByExtrapolating(String::getNextValue) shouldBe 114
        input.sumByExtrapolating(String::getPreviousValue) shouldBe 2
    }

}