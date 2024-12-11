package gg.jte.aoc.v2024

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    private val input = """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
    """.trimIndent()

    @Test
    fun `should get all surrounding words`() {
        parse(input).countXMASes() shouldBe 18
    }
}