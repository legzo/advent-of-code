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

fun List<Int>.toFishCensus(): Map<LanternfishKind, Long> =
    groupingBy { it }
        .eachCount()
        .map { LanternfishKind(it.key) to it.value.toLong()}
        .toMap()


fun Map<LanternfishKind, Long>.countFishWithCounter(counter: Int) =
    this[LanternfishKind(counter)] ?: 0

fun simulate(
    initialCensus: Map<LanternfishKind, Long>,
    numberOfDays: Int
): Long {
    val sequence = generateSequence(initialCensus) { currentState ->
        mapOf(
            LanternfishKind(0) to currentState.countFishWithCounter(1),
            LanternfishKind(1) to currentState.countFishWithCounter(2),
            LanternfishKind(2) to currentState.countFishWithCounter(3),
            LanternfishKind(3) to currentState.countFishWithCounter(4),
            LanternfishKind(4) to currentState.countFishWithCounter(5),
            LanternfishKind(5) to currentState.countFishWithCounter(6),
            LanternfishKind(6) to currentState.countFishWithCounter(7) + currentState.countFishWithCounter(0),
            LanternfishKind(7) to currentState.countFishWithCounter(8),
            LanternfishKind(8) to + currentState.countFishWithCounter(0),
        )
    }

    return sequence.elementAt(numberOfDays).toList().sumOf { it.second }
}

@JvmInline
value class LanternfishKind(
    private val counter: Int
) {
    override fun toString(): String {
        return counter.toString()
    }
}
