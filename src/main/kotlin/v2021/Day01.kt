package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFileAsInts


fun main() {
    val input = getLinesFromFileAsInts("v2021/day_01.txt")

    println(countIncreasingStepsWithSlidingWindow(windowSize = 1, measures = input))
    println(countIncreasingStepsWithSlidingWindow(windowSize = 3, measures = input))

}

fun countIncreasingStepsWithSlidingWindow(
    windowSize: Int,
    measures: List<Int>
): Int =
    measures
        .windowed(windowSize) { it.sum() }
        .zipWithNext()
        .count { it.increases() }


fun Pair<Int, Int>.increases() = second > first