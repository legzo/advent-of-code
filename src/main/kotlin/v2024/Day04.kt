package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day04.txt")
    measureTimeAndPrint {
        parse(input).countXMASes()
    }
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

    fun countXMASes() =
        points
            .filter { it.letter == "X" }
            .flatMap { getSurroundingWords(it) }
            .count { it == "XMAS" }
}

data class Coords(
    val x: Int,
    val y: Int,
)

data class Point(
    val coords: Coords,
    val letter: String,
)