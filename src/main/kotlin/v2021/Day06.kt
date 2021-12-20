package gg.jte.aoc.v2021

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2021/day_06.txt")
        .split(",")
        .map { it.toInt() }
        .toFishCensus()

    measureTimeAndPrint { simulate(initialCensus = input, numberOfDays = 80) }
    measureTimeAndPrint { simulate(initialCensus = input, numberOfDays = 256) }
}

fun List<Int>.toFishCensus(): Map<Lanternfish, Long> =
    groupingBy { it }
        .eachCount()
        .map { (counter, numberOfFish) -> Lanternfish(counter) to numberOfFish.toLong()}
        .toMap()


fun Map<Lanternfish, Long>.countFishAtCounter(counter: Int) =
    this[Lanternfish(counter)] ?: 0

fun simulate(
    initialCensus: Map<Lanternfish, Long>,
    numberOfDays: Int
): Long {
    val sequence = generateSequence(initialCensus) { currentState ->
        mapOf(
            Lanternfish(0) to currentState.countFishAtCounter(1),
            Lanternfish(1) to currentState.countFishAtCounter(2),
            Lanternfish(2) to currentState.countFishAtCounter(3),
            Lanternfish(3) to currentState.countFishAtCounter(4),
            Lanternfish(4) to currentState.countFishAtCounter(5),
            Lanternfish(5) to currentState.countFishAtCounter(6),
            Lanternfish(6) to currentState.countFishAtCounter(7) + currentState.countFishAtCounter(0),
            Lanternfish(7) to currentState.countFishAtCounter(8),
            Lanternfish(8) to + currentState.countFishAtCounter(0),
        )
    }

    return sequence
        .elementAt(numberOfDays)
        .values
        .sumOf { it }
}

@JvmInline
value class Lanternfish(
    private val counter: Int
) {
    override fun toString(): String {
        return counter.toString()
    }
}
