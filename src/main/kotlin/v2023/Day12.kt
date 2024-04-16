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
    private val splittedInput = input.split(" ")

    private val inputToCheck = splittedInput[0]
    val conditions = splittedInput[1].split(",").map { it.toInt() }

    private fun recurr(input: String, context: GroupingContext): List<GroupingContext> =
        if (!context.isValidSoFar() || input.isEmpty())
            listOf(context.closeGroup())
        else when (input.first()) {
            '#' -> recurr(input.drop(1), context.increaseGroupSize())
            '.' -> recurr(input.drop(1), context.closeGroup())

            '?' -> recurr(input.replaceFirst('?', '#'), context) +
                    recurr(input.replaceFirst('?', '.'), context)

            else -> error("")
        }

    val nbOfValidArrangements = recurr(inputToCheck, GroupingContext(validationGroups = conditions))
        .count { it.matchGroups == it.validationGroups }
}

data class GroupingContext(
    val currentGroup: Int = 0,
    val validationGroups: List<Int>,
    val matchGroups: List<Int> = listOf(),
) {
    fun increaseGroupSize() = copy(currentGroup = currentGroup + 1)
    fun closeGroup() = if (currentGroup > 0) {
        copy(currentGroup = 0, matchGroups = matchGroups + listOf(currentGroup))
    } else this

    fun isValidSoFar() = matchGroups.zip(validationGroups).all { (a, b) -> a == b }
}

fun String.totalNbOfValidArrangements(): Int =
    lines().sumOf { Row(it).nbOfValidArrangements }
