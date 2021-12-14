package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = getLinesFromFile("v2021/day_05.txt")

    measureTimeAndPrint { input.parseAsLines().findNumberOfPointsWhereAtLeast2LinesOverlap() }
}

fun Iterable<Line>.findNumberOfPointsWhereAtLeast2LinesOverlap(): Int {
    val extrema = getExtremaPoint(this)

    val horizontalAndVerticalLines = this
        .filter { (start, end) ->
            start.x == end.x || start.y == end.y
        }

    return (0..extrema.x).sumOf { x ->
        (0..extrema.y).count { y ->
            println("$x $y")
            val overlappingLines = horizontalAndVerticalLines
                .filter { it.covers(Point(x, y)) }

            overlappingLines.toList().size >= 2
        }
    }
}

fun getExtremaPoint(lines: Iterable<Line>): Point =
    Point(
        x = lines.maxOf { max(it.start.x, it.end.x) },
        y = lines.maxOf { max(it.start.y, it.end.y) }
    )

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
    fun covers(point: Point): Boolean =
        point.x in setOf(start.x, end.x) && point.y in (min(start.y, end.y)..max(start.y, end.y))
                || point.y in setOf(start.y, end.y) && point.x in (min(start.x, end.x)..max(start.x, end.x))
}