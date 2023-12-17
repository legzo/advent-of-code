package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val input = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw""".trimIndent()

    @Test
    fun `should calculate the sum of the priorities of each misplaced item`() {
        prioritiesOfMisplacedItems(input).sum() shouldBe 157
    }

    @Test
    fun `should calculate the sum of the priorities of each badge`() {
        prioritiesOfBadges(input).sum() shouldBe 70
    }

}
