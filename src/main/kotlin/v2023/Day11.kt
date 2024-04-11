package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import kotlin.math.abs

fun main() {
    val input = getTextFromFile("v2023/day11.txt")
    measureTimeAndPrint {
        Universe(initialStateAsString = input).locateStars().sumOfDistances()
    }
}

data class Star(
    val x: Int,
    val y: Int,
)

data class Universe(
    val initialStateAsString: String,
) {
    private val initialWidth = initialStateAsString.lines().first().length
    private val initialColumns = getColumns(initialStateAsString)
    val expandedStateAsString: String = expand(initialStateAsString)
    private val finalWidth = expandedStateAsString.lines().first().length

    fun locateStars(): List<Star> =
        expandedStateAsString
            .asOneLine()
            .mapIndexedNotNull { index, character ->
                if (character == '#')
                    Star(index % finalWidth, index / finalWidth)
                else
                    null
            }

    private fun expand(initialState: String): String {

        return initialState
            .lines()
            .flatMap { line ->
                val lineWithExpandedColumns = line.expandColumns(initialColumns)
                lineWithExpandedColumns.duplicateIf { lineWithExpandedColumns.shouldBeDuplicated() }
            }
            .joinToString(separator = "\n")
    }

    private fun String.expandColumns(columns: List<String>): String =
        flatMapIndexed { index, character ->
            character.toString().duplicateIf { columns[index].shouldBeDuplicated() }
        }
            .joinToString(separator = "")

    private fun getColumns(initialState: String): List<String> {

        val oneLine = initialState.asOneLine()

        return (0..<initialWidth).map { oneLine.getColumn(initialWidth, forIndex = it) }
    }

    private fun String.asOneLine(): String = replace("\n", "")

    private fun String.duplicateIf(condition: () -> Boolean) =
        if (condition()) listOf(this, this) else listOf(this)

    private fun String.shouldBeDuplicated() = !contains("#")

    private fun String.getColumn(width: Int, forIndex: Int) = filterIndexed { index, _ -> index % width == forIndex }

}

fun <T> Collection<T>.allPairs(acc: Set<Pair<T, T>> = setOf()): Set<Pair<T, T>> {
    if (this.isEmpty())
        return acc

    val asList = toList()
    val first = asList.first()

    val pairs = asList.minus(first).map {
        first to it
    }.toSet()

    return asList.minus(first).allPairs(pairs + acc)
}

infix fun Star.distanceTo(other: Star): Int =
    abs(this.x - other.x) + abs(this.y - other.y)

fun Collection<Star>.sumOfDistances() =
    allPairs()
        .sumOf { it.first distanceTo it.second }