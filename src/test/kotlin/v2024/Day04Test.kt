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
    fun `should get all xmases`() {
        parse(input).countXMASes() shouldBe 18
    }

    @Test
    fun `should get all mases`() {
        parse(input).countMASes() shouldBe 9
    }
}