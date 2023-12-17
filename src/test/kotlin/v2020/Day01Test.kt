package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01Test {

    @Test
    fun `should find pair with sum equal to total`() {
        listOf(1721, 979, 366, 299, 675, 1456)
            .findPairWithSum(2020) shouldBe (1721 to 299)
    }

    @Test
    fun `should find triple with sum equal to total`() {
        listOf(1721, 979, 366, 299, 675, 1456)
            .findTripleMatching { it.first + it.second + it.third == 2020 } shouldBe Triple(979, 366, 675)
    }
    
}
