package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val input = """
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
        """.trimIndent()

    private val inputLines = input
        .split('\n')
        .map { it.toLong() }

    @Test
    fun `should find only invalid number`() {
        inputLines.findInvalidElement(chunkSize = 5) shouldBe 127
    }

    @Test
    fun `should find contiguous range producing invalid number`() {
        inputLines.findContiguousRangeProducingInvalidNumber(chunkSize = 5) shouldBe
                listOf(15L, 25L, 47L, 40)
    }

}
