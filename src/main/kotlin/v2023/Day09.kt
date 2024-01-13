package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day09.txt")
    measureTimeAndPrint { input.sumOfExtrapolatedValues() }
}

fun String.sumOfExtrapolatedValues() =
    lines()
        .sumOf { it.getNextValue() }

fun String.getNextValue() =
    sequenceOfDiffs()
        .map { it.last() }
        .sum()

fun String.sequenceOfDiffs() =
    generateSequence(seed = asIntList()) { current ->
        val next = current.zipWithNext { a, b -> b - a }
        if (next.any { it != 0 }) next else null
    }

fun String.asIntList() =
    split(" ")
        .map { it.toInt() }
