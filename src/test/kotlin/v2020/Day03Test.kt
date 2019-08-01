package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    private val input = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#"""
        .trimIndent()
        .split("\n")

    @Test
    fun `should count trees on the way`() {
        input.countTreesOnTheWay(Slope(3, 1)) shouldBe 7
        input.countTreesOnTheWay(Slope(1, 2)) shouldBe 2
    }

    @Test
    fun `should find multiplied count of trees on the way for multiple slopes`() {
        input
            .findMultipliedTotalOfTreesFor(
                listOf(
                    Slope(x = 1, y = 1),
                    Slope(x = 3, y = 1),
                    Slope(x = 5, y = 1),
                    Slope(x = 7, y = 1),
                    Slope(x = 1, y = 2)
                )
            ) shouldBe 336
    }
}



