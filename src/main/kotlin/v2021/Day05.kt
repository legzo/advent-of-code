package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = getLinesFromFile("v2021/day_05.txt")

    measureTimeAndPrint { input.parseAsLines().findNumberOfPointsWhereAtLeast2LinesOverlap() }
    measureTimeAndPrint { input.parseAsLines().findNumberOfPointsWhereAtLeast2LinesOverlap(includingDiagonalLines = true) }
}

fun Iterable<Line>.findNumberOfPointsWhereAtLeast2LinesOverlap(
    includingDiagonalLines: Boolean = false
): Int {
    return filter { includingDiagonalLines || !it.isDiagonal }
        .flatMap { it.points }
        .groupingBy { it }
        .eachCount()
        .count { (_, occurrences) -> occurrences >= 2 }
}

fun Iterable<String>.parseAsLines() =
    mapNotNull {
        it.parseWithRegex(regex = """([\d]+),([\d]+) -> ([\d]+),([\d]+)""") { (x1, y1, x2, y2) ->
            Line(
                start = Point(x1.toInt(), y1.toInt()),
                end = Point(x2.toInt(), y2.toInt()),
            )
        }
    }

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val start: Point,
    val end: Point
) {

    private val minX = min(start.x, end.x)
    private val maxX = max(start.x, end.x)
    private val minY = min(start.y, end.y)
    private val maxY = max(start.y, end.y)

    val isDiagonal = minX != maxX && minY != maxY

    val points: Collection<Point> = when {
        start.x == end.x ->
            (minY..maxY).map { Point(start.x, it) }
        start.y == end.y ->
            (minX..maxX).map { Point(it, start.y) }
        start.x < end.x && start.y < end.y ->
            (start.x..end.x).mapIndexed { index, x -> Point(x, start.y + index) }
        start.x < end.x && start.y > end.y ->
            (start.x..end.x).mapIndexed { index, x -> Point(x, start.y - index) }
        start.x > end.x && start.y < end.y ->
            (end.x..start.x).mapIndexed { index, x -> Point(x, end.y - index) }
        start.x > end.x && start.y > end.y ->
            (end.x..start.x).mapIndexed { index, x -> Point(x, end.y + index) }
        else -> listOf()
    }
}