package gg.jte.aoc.v2023

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    private val input = """
       Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
       Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
       Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
       Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
       Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
       Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()

    @Test
    fun `should parse cards`() {
        val cards = input.parseAsCards()
        cards shouldHaveSize 6
        cards shouldContain Card(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53))
        cards shouldContain Card(6, listOf(31, 18, 13, 56, 72), listOf(74, 77, 10, 23, 35, 67, 36, 11))
    }

    @Test
    fun `should find winning numbers`() {
        input.parseAsCards()
            .first()
            .findMatchingNumbers() shouldContainExactlyInAnyOrder listOf(48, 83, 17, 86)
    }

    @Test
    fun `should find card score`() {
        input.parseAsCards()
            .calculateScores() shouldBe listOf(8, 2, 2, 1, 0, 0)
    }

    @Test
    fun `should find number of cards after copies`() {
        input.parseAsCards()
            .process()
            .countAllCopies() shouldBe 30
    }
}
