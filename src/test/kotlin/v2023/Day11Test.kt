package gg.jte.aoc.v2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val input = """
    ...#......
    .......#..
    #.........
    ..........
    ......#...
    .#........
    .........#
    ..........
    .......#..
    #...#.....
    """.trimIndent()

    @Test
    fun `should locate stars`() {
        Universe(initialStateAsString = input, expansionFactor = 2).locateStars() shouldBe
                listOf(
                    Star(x = 3, y = 0),
                    Star(x = 7, y = 1),
                    Star(x = 0, y = 2),
                    Star(x = 6, y = 4),
                    Star(x = 1, y = 5),
                    Star(x = 9, y = 6),
                    Star(x = 7, y = 8),
                    Star(x = 0, y = 9),
                    Star(x = 4, y = 9),
                )
    }

    @Test
    fun `should return combinations`() {
        listOf(1, 2, 3, 4).allPairs() shouldBe
                setOf(
                    1 to 2,
                    1 to 3,
                    1 to 4,
                    2 to 3,
                    2 to 4,
                    3 to 4,
                )

        listOf(
            Star(x = 4, y = 0),
            Star(x = 9, y = 1),
            Star(x = 0, y = 2),
            Star(x = 8, y = 5),
            Star(x = 1, y = 6),
            Star(x = 12, y = 7),
            Star(x = 9, y = 10),
            Star(x = 0, y = 11),
            Star(x = 5, y = 11)
        ).allPairs().size shouldBe 36
    }

    @Test
    fun `should calculate distance between stars`() {
        with(Universe(initialStateAsString = input, expansionFactor = 2)) {
            Star(x = 1, y = 5) distanceTo Star(x = 4, y = 9) shouldBe 9
            Star(x = 3, y = 0) distanceTo Star(x = 7, y = 8) shouldBe 15
            Star(x = 0, y = 2) distanceTo Star(x = 9, y = 6) shouldBe 17
            Star(x = 0, y = 9) distanceTo Star(x = 4, y = 9) shouldBe 5
        }
    }

    @Test
    fun `should calculate sum of distances between stars`() {
        with(Universe(initialStateAsString = input, expansionFactor = 2)) {
            locateStars().sumOfDistances() shouldBe 374
        }


    }
}