package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val lines = getLinesFromFile("v2021/day_08.txt")
    measureTimeAndPrint { lines.countOccurrencesOf147And8() }
}


internal fun List<String>.countOccurrencesOf147And8(): Int =
    sumOf { line ->
        line
            .getFourDigitOutputValue()
            .split(" ")
            .count { word -> word.length in setOf(2, 4, 3, 7)  }

    }

private fun String.getFourDigitOutputValue() = substring(61)

