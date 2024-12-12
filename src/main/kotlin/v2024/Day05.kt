package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day05.txt")

    measureTimeAndPrint {
        val rulesAndUpdates = parseAsRulesAndUpdate(input)
        rulesAndUpdates.getValidUpdates().sumOfMiddlePageNumbers()
    }
}

data class RulesAndUpdate(
    val rules: Collection<Rule>,
    val updates: Collection<Update>
) {
    fun getValidUpdates() =
        updates
            .filter { update ->
                rules.all { it isValidFor update }
            }
}

fun List<Update>.sumOfMiddlePageNumbers() =
    sumOf { it.pages[it.pages.size / 2] }

data class Rule(
    val beforePage: Int,
    val afterPage: Int,
) {
    infix fun isValidFor(update: Update) =
        with(update.pages) {
            beforePage !in this
                    || afterPage !in this
                    || indexOf(beforePage) < indexOf(afterPage)
        }

}

data class Update(
    val pages: List<Int>
) {
    constructor(vararg page: Int) : this(pages = page.toTypedArray().toList())
}

fun parseAsRulesAndUpdate(input: String): RulesAndUpdate {
    val (firstPart, secondPart) = input.split("\n\n")

    val rules: List<Rule> = firstPart
        .lines()
        .map {
            val (beforePage, afterPage) = it.split("|")
            Rule(beforePage = beforePage.toInt(), afterPage = afterPage.toInt())
        }

    val updates: List<Update> = secondPart
        .lines()
        .map { Update(it.split(",").map { it.toInt() }) }

    return RulesAndUpdate(
        rules = rules,
        updates = updates,
    )
}
