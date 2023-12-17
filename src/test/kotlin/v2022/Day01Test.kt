package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val input = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000""".trimIndent()


    @Test
    fun `find elf with most calories`() {
        findMostCaloriesCarriedByAnElf(input) shouldBe 24000
    }

    @Test
    fun `find calories carried by top 3 elves`() {
        findCaloriesCarriedByTop3Elves(input) shouldBe 45000
    }
}