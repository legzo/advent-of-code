package gg.jte.aoc.v2024

import gg.jte.aoc.v2024.ItemType.EMPTY
import gg.jte.aoc.v2024.ItemType.OBSTACLE
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val input = """
    ....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...
    """.trimIndent()

    @Test
    fun `should parse input`() {
        val itemsByPosition = parseAsMap(input).itemsByPosition
        itemsByPosition[Position(x = 0, y = 0)]?.type shouldBe EMPTY
        itemsByPosition[Position(x = 4, y = 0)]?.type shouldBe OBSTACLE
    }

    @Test
    fun `should find guard`() {
        val guard = parseAsMap(input).guard
        guard.position shouldBe Position(x = 4, y = 6)
        guard.facing shouldBe Direction.UP
    }

    @Test
    fun `should get guard positions`() {
        val guardMap = parseAsMap(input)
        getGuardPositions(guardMap) shouldHaveSize 41
    }
}