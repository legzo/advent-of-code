package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day05.txt")

    val (updates, rules) = parseAsRulesAndUpdate(input)

    measureTimeAndPrint {
        updates
            .filter(isValid = true, accordingTo = rules)
            .sumOfMiddlePageNumbers()
    }

    measureTimeAndPrint {
        updates
            .filter(isValid = false, accordingTo = rules)
            .map { it.reorderAccordingTo(rules) }
            .sumOfMiddlePageNumbers()
    }
}

data class RulesAndUpdate(
    val updates: Collection<Update>,
    val rules: Collection<Rule>,
)

fun Collection<Update>.filter(isValid: Boolean, accordingTo: Collection<Rule>) =
    with(partition { it.isNotValidFor(accordingTo) }) {
        if (isValid) second else first
    }

fun List<Update>.sumOfMiddlePageNumbers() =
    sumOf { it.pages[it.pages.size / 2] }

data class Rule(
    val beforePage: Int,
    val afterPage: Int,
) {
    infix fun isNotValidFor(update: Update): Boolean =
        with(update.pages) {
            beforePage in this
                    && afterPage in this
                    && indexOf(beforePage) > indexOf(afterPage)
        }

}

data class Update(
    val pages: List<Int>
) {
    constructor(vararg pages: Int) : this(pages = pages.toTypedArray().toList())

    fun isNotValidFor(rules: Collection<Rule>): Boolean =
        firstBrokenRule(rules) != null

    fun firstBrokenRule(rules: Collection<Rule>): Rule? =
        rules.firstOrNull { it isNotValidFor this }

    fun reorderAccordingTo(rules: Collection<Rule>): Update =
        firstBrokenRule(rules)
            ?.let { copy(pages.swapElementsAccordingTo(it)).reorderAccordingTo(rules) }
            ?: this
}

fun List<Int>.swapElementsAccordingTo(rule: Rule): List<Int> {
    val firstIndex = indexOf(rule.beforePage)
    val secondIndex = indexOf(rule.afterPage)
    return toMutableList().apply {
        val temp = this[firstIndex]
        this[firstIndex] = this[secondIndex]
        this[secondIndex] = temp
    }.toList()
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
