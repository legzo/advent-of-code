package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day09.txt")
    measureTimeAndPrint { input.sumByExtrapolating(String::getNextValue) }
    measureTimeAndPrint { input.sumByExtrapolating(String::getPreviousValue) }
}

fun String.sumByExtrapolating(extrapolateBy: (String) -> Int) =
    lines()
        .sumOf(extrapolateBy)

fun String.getNextValue() =
    sequenceOfDiffs()
        .map { it.last() }
        .sum()

fun String.getPreviousValue() =
    sequenceOfDiffs()
        .map { it.first() }
        .toList()
        .reversed()
        .reduce { a, b -> b - a }

fun String.sequenceOfDiffs() =
    generateSequence(seed = asIntList()) { current ->
        val next = current.zipWithNext { a, b -> b - a }
        if (next.any { it != 0 }) next else null
    }

fun String.asIntList() =
    split(" ")
        .map { it.toInt() }
