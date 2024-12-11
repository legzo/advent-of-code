package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day04.txt")
    measureTimeAndPrint { parse(input).countXMASes() }
    measureTimeAndPrint { parse(input).countMASes() }
}

fun parse(input: String) =
    Grid(
        points = input
            .lines()
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    Point(coords = Coords(x, y), letter = char.toString())
                }
            }
    )

data class Grid(
    val points: List<Point>,
    val dimension: Int = points.maxOf { it.coords.x },
) {

    val pointsByCoord = points.associateBy { it.coords }

    private fun getSurroundingWords(point: Point): List<String> =
        with((0..3)) {
            listOf(
                map { Coords(point.coords.x + it, point.coords.y) },
                map { Coords(point.coords.x - it, point.coords.y) },
                map { Coords(point.coords.x, point.coords.y + it) },
                map { Coords(point.coords.x, point.coords.y - it) },
                map { Coords(point.coords.x + it, point.coords.y + it) },
                map { Coords(point.coords.x - it, point.coords.y - it) },
                map { Coords(point.coords.x - it, point.coords.y + it) },
                map { Coords(point.coords.x + it, point.coords.y - it) },
            ).map {
                it.joinToString(separator = "") { pointsByCoord[it]?.letter ?: "" }
            }
        }

    private fun getDiagonalLetters(point: Point): List<Point> =
        listOfNotNull(
            pointsByCoord[Coords(x = point.coords.x + 1, y = point.coords.y + 1)],
            pointsByCoord[Coords(x = point.coords.x + 1, y = point.coords.y - 1)],
            pointsByCoord[Coords(x = point.coords.x - 1, y = point.coords.y + 1)],
            pointsByCoord[Coords(x = point.coords.x - 1, y = point.coords.y - 1)],
        )

    fun countXMASes() =
        points
            .filter { it.letter == "X" }
            .flatMap { getSurroundingWords(it) }
            .count { it == "XMAS" }

    fun countMASes() =
        points
            .filter { it.letter == "A" }
            .map { getDiagonalLetters(it) }
            .count { xLetters ->
                val groupedBy = xLetters.groupBy { it.letter }
                val ms = groupedBy["M"] ?: return@count false

                groupedBy.keys == setOf("M", "S") // only M and S
                        && ms.size == 2 // there has to be 2 Ms
                        && (ms allHaveSame { it.coords.x } || ms allHaveSame { it.coords.y })// with the same x or same y
            }

    private infix fun List<Point>.allHaveSame(predicate: (Point) -> Any): Boolean =
        map(predicate).distinct().size == 1
}

data class Coords(
    val x: Int,
    val y: Int,
)

data class Point(
    val coords: Coords,
    val letter: String,
)