package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val lines = getLinesFromFile("v2021/day_09.txt")

    measureTimeAndPrint { HeightMap.from(lines).sumOfRiskLevels }
}

data class HeightMapPoint(
    val x: Int,
    val y: Int,
    val height: Int
) {
    fun isLowPointFor(heightMap: HeightMap): Boolean =
        heightMap
            .getNeighboursOf(this)
            .all { it.height > this.height }
}

data class HeightMap(
    val points: List<HeightMapPoint>
) {

    val lowPoints = points.filter {
            it.isLowPointFor(this)
        }

    val sumOfRiskLevels = lowPoints.sumOf {
        it.height + 1
    }

    fun getNeighboursOf(point: HeightMapPoint): List<HeightMapPoint> =
        with(point) {
            listOfNotNull(
                pointAt(x - 1, y),
                pointAt(x + 1, y),
                pointAt(x, y - 1),
                pointAt(x, y + 1),
            )
        }

    private fun pointAt(x: Int, y: Int): HeightMapPoint? =
        points.firstOrNull { it.x == x && it.y == y }

    companion object {
        fun from(lines: List<String>): HeightMap {
            val points = lines.flatMapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    HeightMapPoint(x, y, char.digitToInt())
                }
            }
            return HeightMap(points)
        }
    }
}