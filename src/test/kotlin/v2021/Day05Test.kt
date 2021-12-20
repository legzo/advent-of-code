package v2021

import gg.jte.aoc.v2021.Line
import gg.jte.aoc.v2021.Point
import gg.jte.aoc.v2021.findNumberOfPointsWhereAtLeast2LinesOverlap
import gg.jte.aoc.v2021.parseAsLines
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun `should find number of points where at least two lines overlap`() {
        listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )
            .parseAsLines()
            .findNumberOfPointsWhereAtLeast2LinesOverlap() shouldBe 5
    }

    @Test
    fun `should find number of points where at least two lines overlap including diagonals`() {
        listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )
            .parseAsLines()
            .findNumberOfPointsWhereAtLeast2LinesOverlap(includingDiagonalLines = true) shouldBe 12
    }

    @Test
    fun `should determine if a line covers a point`() {
        listOf(
            Point(1,1),
            Point(1,2),
            Point(1,3)
        ).map {
            it shouldBeIn Line(Point(1,1), Point(1,3)).points
        }

        listOf(
            Point(2,1),
            Point(5,7),
            Point(1,4)
        ).map {
            it shouldNotBeIn Line(Point(1,1), Point(1,3)).points
        }

        listOf(
            Point(9,7),
            Point(8,7),
            Point(7,7)
        ).map {
            it shouldBeIn Line(Point(9,7), Point(7,7)).points
        }
    }

    @Test
    fun `should determine if a line covers a point for diagonals`() {
        listOf(
            Point(1,1),
            Point(2,2),
            Point(3,3)
        ).map {
            it shouldBeIn Line(Point(1,1), Point(3,3)).points
        }

        listOf(
            Point(9,7),
            Point(8,8),
            Point(7,9)
        ).map {
            it shouldBeIn Line(Point(9,7), Point(7,9)).points
        }

        listOf(
            Point(9,9),
            Point(7,7),
        ).map {
            it shouldNotBeIn Line(Point(9,7), Point(7,9)).points
        }
    }
}