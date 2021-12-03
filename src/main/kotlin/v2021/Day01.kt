package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFileAsInts


fun main() {
    val input = getLinesFromFileAsInts("v2021/day_01.txt")

    println(countDecreaseSteps(input))
    println(countDecreaseStepsWithSlidingWindow(input))

}

fun countDecreaseSteps(measures: List<Int>): Int =
    measures
        .zipWithNext()
        .count { it.decreases() }

fun countDecreaseStepsWithSlidingWindow(measures: List<Int>): Int =
    measures
        .windowed(3)
        .map { it.sum() }
        .zipWithNext()
        .count { it.decreases() }


fun Pair<Int, Int>.decreases() = second > first