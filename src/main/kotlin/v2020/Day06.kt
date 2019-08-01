package gg.jte.aoc.v2020

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {

    val input = getTextFromFile("v2020/day_06.txt")

    // Part 1
    measureTimeAndPrint {
        input
            .parseGroups()
            .sumOf { it.numberOfQuestionsAnsweredWithYesByAtLeastOnePerson }
    }

    // Part 2
    measureTimeAndPrint {
        input
            .parseGroups()
            .sumOf { it.numberOfQuestionsAnsweredWithYesByEveryone }
    }
}

fun String.parseGroups(): List<Group> =
    this
        .split("\n\n")
        .map { groupResultsAsString ->
            Group(results = groupResultsAsString.split("\n"))
        }


data class Group(
    val results: List<String>
) {
    val numberOfQuestionsAnsweredWithYesByAtLeastOnePerson =
        results.flatMap { it.toSet() }.toSet().count()

    val numberOfQuestionsAnsweredWithYesByEveryone =
        ('a'..'z').count { char ->
            results.all { char in it }
        }
}
