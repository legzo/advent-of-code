package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val input = """
        16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4
        """.trimIndent()

    private val inputLines = input
        .split('\n')
        .map { it.toInt() }

    private val otherInput = """
        28
        33
        18
        42
        31
        14
        46
        20
        48
        47
        24
        23
        49
        45
        19
        38
        39
        11
        1
        32
        25
        35
        8
        17
        7
        9
        4
        2
        34
        10
        3
        """.trimIndent()

    private val otherInputLines = otherInput
        .split('\n')
        .map { it.toInt() }

    @Test
    fun `should find compatible adapters list`() {
        inputLines.findCompatibleAdaptersList() shouldBe
                listOf(1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19)
    }

    @Test
    fun `should get voltage difference`() {
        listOf(1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19).getVoltageDifferences() shouldBe
                mapOf(1 to 7, 3 to 5)
    }

    @Test
    fun `should count all possible arrangements`() {
        inputLines.countAllPossibleArrangements() shouldBe 8
        otherInputLines.countAllPossibleArrangements() shouldBe 19208
    }

    @Test
    fun `should get voltage difference in one step`() {
        otherInputLines
            .findCompatibleAdaptersList()
            .getVoltageDifferences() shouldBe mapOf(1 to 22, 3 to 10)
    }

}
