package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {


    @Test
    fun `find elf with most calories`() {
        val input = """
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

        findMostCaloriesCarriedByAnElf(input) shouldBe 24000
    }

}