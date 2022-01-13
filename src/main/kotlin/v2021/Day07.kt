package gg.jte.aoc.v2021

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.shouldNotHappen
import kotlin.math.abs

fun main() {
    val offsets = getTextFromFile("v2021/day_07.txt")
        .split(",")
        .map { it.toInt() }

    measureTimeAndPrint { findMinimumFuelAmountRequired(offsets, CostStrategies::constantCost) }
    measureTimeAndPrint { findMinimumFuelAmountRequired(offsets, CostStrategies::linearIncreasingCost) }
}

fun findMinimumFuelAmountRequired(
    offsets: List<Int>,
    countFuel: (Int, Int) -> Int
): Int {
    val min = offsets.minOrNull()
    val max = offsets.maxOrNull()
    if (min == null || max == null) shouldNotHappen()
    return (min..max).minOf { currentTarget ->
        offsets.sumOf {
            countFuel(currentTarget, it)
        }
    }
}

object CostStrategies {

    fun constantCost(
        startPosition: Int,
        finalPosition: Int
    ) = abs(startPosition - finalPosition)

    fun linearIncreasingCost(
        startPosition: Int,
        finalPosition: Int
    ): Int {
        val distance = abs(startPosition - finalPosition)
        return distance * (distance + 1) / 2
    }
}
