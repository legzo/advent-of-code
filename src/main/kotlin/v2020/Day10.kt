package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFileAsInts
import gg.jte.aoc.measureTimeAndPrint

fun main() {

    val numbers = getLinesFromFileAsInts("v2020/day_10.txt")

    // Part 1
    measureTimeAndPrint {
        numbers
            .findCompatibleAdaptersList()
            .getVoltageDifferences()
            .values
            .reduce(Int::times)
    }

    // Part 2
    measureTimeAndPrint {
        numbers.countAllPossibleArrangements()
    }

}

fun List<Int>.findCompatibleAdaptersList(): List<Int> {
    return this.sorted()
}

fun List<Int>.getVoltageDifferences(): Map<Int, Int> =
    (listOf(0) + this)
        .zipWithNext { current, next -> next - current }
        .plus(3)
        .groupingBy { it }
        .eachCount()

fun List<Int>.findAllCompatibleAdaptersFor(current: Int): List<Int> =
    this.filter { it in (current + 1)..(current + 3) }

fun List<Int>.countAllPossibleArrangements(): Int {
    val currentList = listOf(0) + this
    return countPossibilitiesForFirstLevel(0, currentList.sorted()).size
}

private fun countPossibilitiesForFirstLevel(currentValue: Int, currentList: List<Int>): List<Int> {
    val allCompatibleAdapters = currentList.findAllCompatibleAdaptersFor(currentValue)

    if (allCompatibleAdapters.isEmpty())
        return listOf(currentValue)

    return allCompatibleAdapters.flatMap {
        countPossibilitiesForFirstLevel(it, currentList - currentValue - it)
    }


}