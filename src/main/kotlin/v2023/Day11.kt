package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day11.txt")
    measureTimeAndPrint {
        with(Universe(initialStateAsString = input, expansionFactor = 2)) {
            locateStars().sumOfDistances()
        }
    }

    measureTimeAndPrint {
        with(Universe(initialStateAsString = input, expansionFactor = 1000000)) {
            locateStars().sumOfDistances()
        }
    }
}

data class Star(
    val x: Int,
    val y: Int,
)

data class Universe(
    val initialStateAsString: String,
    val expansionFactor: Int,
) {
    private val initialWidth = initialStateAsString.lines().first().length
    private val initialColumns = getColumns(initialStateAsString)
    private val initialRows = initialStateAsString.lines()

    private val columnsThatShouldBeExpanded = initialColumns.filterShouldBeExpanded()
    private val rowsThatShouldBeExpanded = initialRows.filterShouldBeExpanded()

    fun locateStars(): List<Star> =
        initialStateAsString
            .asOneLine()
            .mapIndexedNotNull { index, character ->
                if (character == '#')
                    Star(index % initialWidth, index / initialWidth)
                else
                    null
            }

    infix fun Star.distanceTo(other: Star): Long =
        getIndices(x, other.x, columnsThatShouldBeExpanded) + getIndices(y, other.y, rowsThatShouldBeExpanded)

    fun Collection<Star>.sumOfDistances() =
        allPairs()
            .sumOf { it.first distanceTo it.second }

    private fun List<String>.filterShouldBeExpanded(): List<Int> =
        mapIndexedNotNull { index, string -> if (string.shouldBeExpanded()) index else null }

    private fun getColumns(initialState: String): List<String> =
        (0..<initialWidth)
            .map { initialState.asOneLine().getColumn(initialWidth, forIndex = it) }

    private fun String.asOneLine(): String = replace("\n", "")

    private fun String.shouldBeExpanded() = !contains("#")

    private fun String.getColumn(width: Int, forIndex: Int) = filterIndexed { index, _ -> index % width == forIndex }

    private fun getIndices(firstCoord: Int, secondCoord: Int, coordsThatShouldBeExpanded: List<Int>): Long {
        val xs = listOf(firstCoord, secondCoord).sorted()
        return (xs[0] + 1..xs[1])
            .sumOf { if (it in coordsThatShouldBeExpanded) expansionFactor.toLong() else 1L }
    }
}

fun <T> Collection<T>.allPairs(acc: Set<Pair<T, T>> = setOf()): Set<Pair<T, T>> {
    if (this.isEmpty())
        return acc

    val asList = toList()
    val first = asList.first()
    val pairs = asList.minus(first).map { first to it }.toSet()

    return asList.minus(first).allPairs(pairs + acc)
}
