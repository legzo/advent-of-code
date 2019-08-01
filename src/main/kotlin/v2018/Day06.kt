package gg.jte.aoc.v2018

import gg.jte.aoc.getLinesFromFile
import kotlin.math.abs

fun main() {

    val grid = Grid(getLinesFromFile("v2018/day_06.txt").map {
        val (x, y) = it.split(", ")
        Point(x.toInt(), y.toInt())
    })
    println(grid.largestAreaSize) // 5532
    println(grid.getSafestAreaSizeFor(10000)) // 36216

}

data class Point(val x: Int, val y: Int)

typealias Center = Point

data class Grid(val centers: List<Center>) {

    val min = Point(
        x = centers.minByOrNull { it.x }!!.x,
        y = centers.minByOrNull { it.y }!!.y
    )

    val max = Point(
        x = centers.maxByOrNull { it.x }!!.x,
        y = centers.maxByOrNull { it.y }!!.y
    )

    private val allPoints =
        (min.y..max.y).flatMap { y ->
            (min.x..max.x).map { x ->
                Point(x, y)
            }
        }

    val largestAreaSize: Int
        get() = pointsToClosestCenter()
            .groupBy { it.second }
            .mapValues { it.value.map { (point, _) -> point } }
            .filter { (_, pointsOfRegion) -> pointsOfRegion.none(::isTouchingTheBorder) }
            .map { it.value.size }
            .maxOrNull() ?: 0

    fun getSafestAreaSizeFor(maxDistance: Int) =
        allPoints
            .count { point -> centers.sumOf { point.distanceTo(it) } < maxDistance }

    private fun pointsToClosestCenter(): List<Pair<Point, Center>> {
        return allPoints
            .mapNotNull { point ->
                val closestCenters = point.distancesToCenters()
                    .minByOrNull { it.key }!!
                    .value

                if (closestCenters.size == 1) {
                    point to closestCenters[0]
                } else null
            }
    }

    private fun isTouchingTheBorder(point: Point) =
        point.x == min.x || point.x == max.x || point.y == min.y || point.y == max.y

    private fun Point.distancesToCenters(): Map<Int, List<Center>> = centers.groupBy { distanceTo(it) }

    private fun Point.distanceTo(other: Point) = abs(other.x - x) + abs(other.y - y)

}