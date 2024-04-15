package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day12.txt")
    measureTimeAndPrint {
        input.totalNbOfValidArrangements()
    }
}

data class Row(val input: String) {
    val conditions = input.split(" ")[1].split(",").map { it.toInt() }
    val allArrangements = input.allArrangements()

    private fun String.allArrangements(): List<String> =
        if (!contains("?")) listOf(this)
        else
            (replaceFirst("?", "#")).allArrangements() +
                    (replaceFirst("?", ".")).allArrangements()

    val nbOfValidArrangements =
        allArrangements.count { it.getGroups() == conditions }
}

data class GroupingContext(
    val currentGroup: Int = 0,
    val allGroups: List<Int> = listOf()
) {
    fun increaseGroupSize() = copy(currentGroup = currentGroup + 1)
    fun closeGroup() = if (currentGroup != 0) {
        copy(allGroups = allGroups + listOf(currentGroup), currentGroup = 0)
    } else this
}

fun String.getGroups(): List<Int> =
    fold(GroupingContext()) { acc, char ->
        when (char) {
            '#' -> acc.increaseGroupSize()
            else -> acc.closeGroup()
        }
    }.closeGroup()
        .allGroups

fun String.totalNbOfValidArrangements(): Int =
    lines().sumOf { Row(it).nbOfValidArrangements }
