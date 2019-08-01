package gg.jte.aoc.v2018

import gg.jte.aoc.getLinesFromFile

fun main() {
    println(
        getLinesFromFile("v2018/day_01.txt")
            .calculateFrequencyDrift()
    ) // 490

    println(
        getLinesFromFile("v2018/day_01.txt")
            .findFirstRepeatingFrequency()
    ) // 70357
}


fun List<String>.calculateFrequencyDrift(startingFrequency: Int = 0) =
    fold(startingFrequency, ::calculateNewFrequency)

fun List<String>.findFirstRepeatingFrequency(
    previousFrequencies: List<Int> = listOf(0)
): Int {
    val historyOfFrequencies = fold(previousFrequencies) { pastFrequencies, change ->
        val newFrequency = pastFrequencies.last().applyChange(change)
        if (pastFrequencies.contains(newFrequency)) return newFrequency
        pastFrequencies + newFrequency
    }

    return findFirstRepeatingFrequency(historyOfFrequencies)
}

private fun calculateNewFrequency(frequency: Int, change: String) = frequency.applyChange(change)
private fun Int.applyChange(change: String) = this + change.toInt()


