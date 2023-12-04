package v2023

import gg.jte.aoc.v2023.findCalibrationValues
import gg.jte.aoc.v2023.replaceLiteralDigits
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    private val input = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
        """.trimIndent()

    private val inputPart2 = """
        eightwoeighthree
        two1nine
        eightwothree
        abcone2threexyz
        eightwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
        """.trimIndent()

    @Test
    fun `should find calibration values`() {
        input.findCalibrationValues() shouldBe listOf(12, 38, 15, 77)
    }

    @Test
    fun `should replace literal digits`() {
        inputPart2.replaceLiteralDigits() shouldBe """
        8wo8hree
        219
        8wo3
        abc123xyz
        8w134
        49872
        z1ight234
        7pqrst6teen
        """.trimIndent()
    }

    @Test
    fun `should find calibration values for part 2`() {
        inputPart2
            .replaceLiteralDigits()
            .findCalibrationValues() shouldBe listOf(88, 29, 83, 13, 84, 42, 14, 76)
    }
}