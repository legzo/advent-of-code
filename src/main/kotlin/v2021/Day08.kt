package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val lines = getLinesFromFile("v2021/day_08.txt")
    measureTimeAndPrint { lines.countOccurrencesOf147And8() }
}

private val numberOfDigitsFor = mapOf(
    0 to 6,
    1 to 2,
    2 to 5,
    3 to 5,
    4 to 4,
    5 to 5,
    6 to 6,
    7 to 3,
    8 to 7,
    9 to 6,
)

internal fun List<String>.countOccurrencesOf147And8(): Int =
    sumOf { line ->
        line
            .getFourDigitOutputValue()
            .split(" ")
            .count { word -> word.length in setOf(1, 4, 7, 8).map { numberOfDigitsFor[it] }  }

    }

private fun String.getFourDigitOutputValue() = substring(61)

