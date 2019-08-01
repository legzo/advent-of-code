package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getLinesFromFile("v2020/day_05.txt")

    // Part 1
    measureTimeAndPrint { input.mapToSeatIds().maxOf { it } }

    // Part 2
    measureTimeAndPrint { findMissingSeat(input) }
}

private fun findMissingSeat(input: List<String>): Int {
    val unmatchedPair = input
        .mapToSeatIds()
        .sorted()
        .zipWithNext()
        .first { (current, next) -> next != current + 1 }

    return unmatchedPair.first + 1
}

private fun List<String>.mapToSeatIds() =
    map { Seat.parse(it).id }

data class Seat(
    val row: Int,
    val column: Int
) {

    val id = row * 8 + column

    companion object {
        private const val numberOfCharactersForColumn = 3
        fun parse(asString: String): Seat {

            val rowAsString = asString.dropLast(numberOfCharactersForColumn)
            val columnAsString = asString.takeLast(numberOfCharactersForColumn)

            return Seat(
                row = rowAsString.asBooleanWith('B', 'F'),
                column = columnAsString.asBooleanWith('R', 'L')
            )
        }
    }
}

fun String.asBooleanWith(for1: Char, for0: Char) =
    this
        .replace(for1, '1')
        .replace(for0, '0')
        .toInt(2)
