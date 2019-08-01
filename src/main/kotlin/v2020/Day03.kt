package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {

    val input = getLinesFromFile("v2020/day_03.txt")

    // Part 1
    measureTimeAndPrint { input.countTreesOnTheWay(Slope(x = 3, y = 1)) }

    // Part 2
    measureTimeAndPrint {
        input.findMultipliedTotalOfTreesFor(
            listOf(
                Slope(x = 1, y = 1),
                Slope(x = 3, y = 1),
                Slope(x = 5, y = 1),
                Slope(x = 7, y = 1),
                Slope(x = 1, y = 2)
            )
        )
    }
}

data class Slope(
    val x: Int,
    val y: Int
)

fun List<String>.findMultipliedTotalOfTreesFor(slopes: List<Slope>) =
    slopes.map { slope -> countTreesOnTheWay(slope).toLong() }
        .reduce { acc, current -> acc * current }

fun List<String>.countTreesOnTheWay(slope: Slope) =
    (indices step slope.y)
        .count { yPosition ->
            val currentLine = this[yPosition]
            val movementsCount = yPosition / slope.y
            val xPosition = (slope.x * movementsCount) % currentLine.length
            currentLine[xPosition].isTree()
        }

private fun Char.isTree() = this == '#'
