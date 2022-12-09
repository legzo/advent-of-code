package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    private val input = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
        """.trimIndent()


    @Test
    fun `should count fully overlapping ranges`() {
        countFullyOverlappingRanges(input) shouldBe 2
    }

    @Test
    fun `should count overlapping ranges`() {
        countOverlappingRanges(input) shouldBe 4
    }


}
